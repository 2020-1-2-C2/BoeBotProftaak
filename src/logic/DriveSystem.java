package logic;

import hardware.DirectionalServo;
import hardware.LineFollower;
import hardware.ServoMotor;
import utils.LineFollowCallback;
import utils.Motor;
import utils.TimerWithState;
import utils.Updatable;

/**
 * Class which controls an object which implements the Motor interface
 * Contains methods for controlling the Motor object and for following a route
 * implements the Updatable interface, which updates the Motor object and allows for turning around at the end of a route
 * implements the LineFollowCallback interface which allows it to receive the lineposition from the LineFollower sensors, which allows it to follow a route if so desired
 * @author Meindert Kempe, Berend de Groot, Martijn de Kam, Casper Lous
 */
public class DriveSystem implements Updatable, LineFollowCallback {
    private Motor motor;
    private static final int STEPS = 10;
    private static final int MAX_SPEED = 100;
    private int currentMaxSpeed = MAX_SPEED;
    public static final int MIN_SPEED = MAX_SPEED / STEPS;
    private int currentSpeed = 0;
    private int currentSpeedRight = 0;
    private int currentSpeedLeft = 0;
    private int direction = 1;
    private boolean followLine = false;
    private int followSpeed;

    public static final int FORWARD = 1;
    public static final int BACKWARD = -1;
    public static final boolean RIGHT = true;
    public static final boolean LEFT = false;

    private boolean followingRoute = false;
    private Route route;
    private boolean turningLeft = false;
    private boolean turningRight = false;
    private boolean adjustingPosition = false;
    private boolean turnAtEnd = false;
    private boolean rotateFully = false;
    private boolean isRotateFullyPartOneComplete = false;
    private TimerWithState routeDestinationWaitTimer = new TimerWithState(10000, false);
    private TimerWithState crossRoadTimer = new TimerWithState(1000, true);
    private TimerWithState drivingSlightlyForwardsBeforeTurningTimer = new TimerWithState(1200, false);
    private TimerWithState notOnLineTimer = new TimerWithState(2000, false);
    private TimerWithState routeEndTimer = new TimerWithState(10000, false);
    private boolean hasTurnedAroundAtTheEndOfRoute = false;

    private TimerWithState drivingBackAtTheEndOfTheRouteTimer = new TimerWithState(500, false);

    /**
     * Constructor for DriveSystem class.
     */
    public DriveSystem() {
        this.motor = new ServoMotor(new DirectionalServo(Configuration.servoMotor1PinId, DirectionalServo.RIGHT_SIDE_SERVO_ORIENTATION),
                new DirectionalServo(Configuration.servoMotor2PinId, DirectionalServo.LEFT_SIDE_SERVO_ORIENTATION));
    }

    /**
     * @param speed value between 0 and 100
     */
    public void setSpeed(int speed) {
        if (speed > this.currentMaxSpeed) {
            speed = this.currentMaxSpeed;
        } else if (speed < 0) {
            speed = 0;
        }
        speed = speed * this.direction;
        this.currentSpeed = speed;
        this.currentSpeedLeft = this.currentSpeed;
        this.currentSpeedRight = this.currentSpeed;
        this.motor.goToSpeed(speed);
    }

    /**
     * @param direction 1 is forward, -1 is backward
     */
    public void setDirection(int direction) {
        if (direction != this.direction && (direction == FORWARD || direction == BACKWARD)) {
            this.direction = direction;
        }

        // always set the speed to min when the direction is tried to change
        setSpeed(MIN_SPEED);
    }

    /**
     * @param direction 1 is forward, -1 is backward
     */
    public void setDirectionNoSpeed(int direction) {
        if (direction != this.direction && (direction == FORWARD || direction == BACKWARD)) {
            this.direction = direction;
        }
    }

    /**
     * Auto-generated getter for <code>direction</code>.
     * @return direction of BoeBot as integer.
     */
    public int getDirection() {
        return this.direction;
    }

    /**
     * Turn left at the lowest speed.
     */
    public void turnLeft() {
        turn(LEFT);
    }

    /**
     * Turn right at the lowest speed.
     */
    public void turnRight() {
        turn(RIGHT);
    }

    /**
     * Turn the bot on the spot.
     * @param direction true = right, false = left.
     */
    private void turn(boolean direction) {
        if (direction) {
            this.currentSpeedRight = -MIN_SPEED;
            this.currentSpeedLeft = MIN_SPEED;
        } else {
            this.currentSpeedRight = MIN_SPEED;
            this.currentSpeedLeft = -MIN_SPEED;
        }
        this.motor.goToSpeedRight(this.currentSpeedRight * this.direction);
        this.motor.goToSpeedLeft(this.currentSpeedLeft * this.direction);
    }

    /**
     * Turn the bot with the current speed setting.
     * @param direction Current driving direction.
     * @param turnSpeed The speed the bot has to use while making this turn.
     */
    public void turnWithExistingSpeedAndTurnSpeed(boolean direction, int turnSpeed) {
        if (direction) {
            this.currentSpeedRight = this.currentSpeed - turnSpeed;
            this.currentSpeedLeft = this.currentSpeed + turnSpeed;
            if (this.currentSpeedLeft > 100) {
                this.currentSpeedLeft = 100;
                this.currentSpeedRight = 100 - turnSpeed;
            }

        } else {
            this.currentSpeedRight = this.currentSpeed + turnSpeed;
            this.currentSpeedLeft = this.currentSpeed - turnSpeed;
            if (this.currentSpeedRight > 100) {
                this.currentSpeedRight = 100;
                this.currentSpeedLeft = 100 - turnSpeed;
            }

        }

        this.motor.goToSpeedRight(this.currentSpeedRight * this.direction);
        this.motor.goToSpeedLeft(this.currentSpeedLeft * this.direction);
    }

    /**
     * Gradually stops the BoeBot.
     */
    public void stop() {
        this.currentSpeed = 0;
        this.currentSpeedLeft = this.currentSpeed;
        this.currentSpeedRight = this.currentSpeed;
        this.motor.goToSpeed(0);
    }

    /**
     * Stops immediatly without it being an emergency, also doesn't stop route following.
     */
    public void immediateStop() {
        this.currentSpeed = 0;
        this.currentSpeedLeft = this.currentSpeed;
        this.currentSpeedRight = this.currentSpeed;
        this.motor.goToSpeed(0);
        this.motor.immediateStop();
    }

    /**
     * Immediately stops the BoeBot and any route following.
     */
    public void emergencyStop() {
        this.currentMaxSpeed = 0;
        this.currentSpeed = this.currentMaxSpeed;
        this.currentSpeedLeft = this.currentMaxSpeed;
        this.currentSpeedRight = this.currentMaxSpeed;
        this.emergencyStopFollowingRoute();
        this.motor.emergencyStop();
    }

    /**
     * @param follow if true the BoeBot will start line following functionality
     */
    public void setFollowLine(boolean follow) {
        this.followLine = follow;
        this.followSpeed = 10;
        setDirectionNoSpeed(FORWARD);
    }

    /**
     * @return current maximum speed.
     */
    public int getCurrentMaxSpeed() {
        return this.currentMaxSpeed;
    }

    /**
     * @param currentMaxSpeed new maximum speed
     */
    public void setCurrentMaxSpeed(int currentMaxSpeed) {
        if (currentMaxSpeed > MAX_SPEED) {
            currentMaxSpeed = MAX_SPEED;
        }
        this.currentMaxSpeed = currentMaxSpeed;
    }

    /**
     * @return current speed
     */
    public int getCurrentSpeed() {
        return this.currentSpeed;
    }

    /**
     * This class' update() method.
     */
    @Override
    public void update() {
        this.motor.update();

        // When seeing a crossroads while following a route and the next step is left or right, then the bot first drives forwards a little bit
        // After a timer expires it stops and starts turning the appropriate direction
        if (this.drivingSlightlyForwardsBeforeTurningTimer.timeout()) {
            System.out.println("Driving slightly forwards before turning");
            immediateStop();
            if (this.turningLeft) {
                turnLeft();
            } else if (this.turningRight) {
                turnRight();
            } else if (this.rotateFully) {
                turnLeft();
            }
            this.drivingSlightlyForwardsBeforeTurningTimer.setOn(false);
        }

        // logic for resuming a route after waiting for some time at a destination
        if (this.routeDestinationWaitTimer.timeout()) {
            System.out.println("Resuming the route after waiting at the destination");
            this.resumeRoute();
            this.routeDestinationWaitTimer.setOn(false);
        }

        // logic for recognising when the bot is done driving backwards at the end of the route and then starting to turn
        if (this.drivingBackAtTheEndOfTheRouteTimer.timeout()) {
            System.out.println("Done driving backwards at the end of the route");
            this.drivingBackAtTheEndOfTheRouteTimer.setOn(false);
            this.immediateStop();
            // Start turning around to face the other direction.
            System.out.println("Starting to turn around to face the other direction at the end of the route");
            this.turnAtEnd = true;
            // Can only turn left, see onLineFollow method else if statement with this.turnAtEnd for elaboration.
            turnLeft();
        }

        // at the end of the route wait some time before resuming
        if (this.routeEndTimer.timeout()) {
            this.routeEndTimer.setOn(false);
            if (!this.hasTurnedAroundAtTheEndOfRoute) {
                // logic for turning around at the end of the route to go back to the start
                this.route.reverse();
                System.out.println("Reversed route: " + this.route.getRoute());
                System.out.println("Start driving backwards at the end of the route");
                this.drivingBackAtTheEndOfTheRouteTimer.setOn(true);
                this.setDirection(BACKWARD);
            } else {
                System.out.println("FINISHED WITH THE ROUTE! BACK AT START!");
            }
        }
    }

    /**
     * Start following the provided route.
     *
     * @param route Route object to use
     */
    public void followRoute(Route route) {
        // first reset all old values in case of a shutdown in the middle of a route
        this.resetBooleanValues();
        // then initialise the route following
        System.out.println("Starting with following a route in DriveSystem");
        this.setFollowingRoute(true);
        this.route = route;
        this.setFollowLine(true);
        this.crossRoadTimer.setOn(true);
        this.crossRoadTimer.setInterval(1000);
        System.out.println("Route: " + this.route.getRoute());
    }

    /**
     * Get the next step of the route and take the appropriate action to follow it.
     */
    private void routeNextStep() {
        if (this.route == null) {
            // if route doesn't exist then you can't do this method
            return;
        }
        
        int nextDirection = this.route.nextDirection();
        System.out.println("Next direction in the route: " + nextDirection);
        switch (nextDirection) {
            case Route.FORWARD:
                this.setSpeed(this.followSpeed);
                break;
            case Route.LEFT:
                this.crossRoadTimer.setInterval(7000);
                System.out.println("Turning around during the route");
                this.turningLeft = true;
                this.setSpeed(MIN_SPEED);
                this.drivingSlightlyForwardsBeforeTurningTimer.setOn(true);
                break;
            case Route.RIGHT:
                this.crossRoadTimer.setInterval(7000);
                System.out.println("Turning around during the route");
                this.turningRight = true;
                this.setSpeed(MIN_SPEED);
                this.drivingSlightlyForwardsBeforeTurningTimer.setOn(true);
                break;
            case Route.ROTATE:
                this.crossRoadTimer.setInterval(12000);
                this.rotateFully = true;
                this.drivingSlightlyForwardsBeforeTurningTimer.setOn(true);
                break;
            case Route.NONE:
                System.out.println("Route has ended so stop for now");
                this.stopFollowingRoute();
                this.immediateStop();
                this.routeEndTimer.setOn(true);
                break;
            case Route.DESTINATION:
                System.out.println("Reached a destination in the route, waiting for some time");
                this.stopFollowingRoute();
                this.immediateStop();
                this.routeDestinationWaitTimer.setOn(true);
                break;
            default:
                // If there are no valid instructions, stop following the route.
                this.stopFollowingRoute();
                this.stop();
                break;
        }
    }

    /**
     * @return value of followingRoute
     */
    public boolean isFollowingRoute() {
        return this.followingRoute;
    }

    /**
     * Stop following the route.
     */
    public void stopFollowingRoute() {
        this.setFollowingRoute(false);
        this.setFollowLine(false);
    }

    /**
     * Method that stops the route following using the stopFollowingRoute method.
     * Also resets all boolean values and timers used in the class so it doesn't suddenly start running again.
     */
    private void emergencyStopFollowingRoute() {
        stopFollowingRoute();
        resetBooleanValues();
    }

    /**
     * Reset all boolean and TimerWithState private attributes to be false/off
     */
    private void resetBooleanValues() {
        this.turningLeft = false;
        this.turningRight = false;
        this.adjustingPosition = false;
        this.turnAtEnd = false;
        this.routeDestinationWaitTimer.setOn(false);
        this.drivingSlightlyForwardsBeforeTurningTimer.setOn(false);
        this.notOnLineTimer.setOn(false);
        this.hasTurnedAroundAtTheEndOfRoute = false;
        this.rotateFully = false;
        this.isRotateFullyPartOneComplete = false;
        this.routeEndTimer.setOn(false);
    }

    /**
     * Resume following of route after it is interrupted.
     */
    public void resumeRoute() {
        if (this.route != null) {
            this.followingRoute = true;
            this.setFollowLine(true);
        }
    }

    /**
     * Set the value of following route.
     * @param followingRoute value to set
     */
    private void setFollowingRoute(boolean followingRoute) {
        this.followingRoute = followingRoute;
    }

    /**
     * This method detects the lines while following a route, and run the correct directions.
     */
    @Override
    public void onLineFollow(LineFollower.LinePosition linePosition) {
        //Following the line normally while not turning
        if (this.followLine && !this.turningRight && !this.turningLeft && !this.adjustingPosition && !this.rotateFully) {
            switch (linePosition) {
                case NOT_ON_LINE:
                    if (!this.notOnLineTimer.isOn()) {
                        this.notOnLineTimer.setOn(true);
                    } else if (this.notOnLineTimer.timeout()) {
                        this.stopFollowingRoute();
                        this.immediateStop();
                        System.out.println("Not on line for too long, stopping");
                        this.notOnLineTimer.setOn(false);
                    }

                    break;
                case ON_LINE:
                    this.setSpeed(this.followSpeed);
                    this.notOnLineTimer.mark();
                    break;
                case LEFT_OF_LINE:
                    System.out.println("Left of line, should stop and turn");
                    if (!this.adjustingPosition) {
                        System.out.println("Starting to adjust position");
                        this.immediateStop();
                        this.turnRight();
                        this.adjustingPosition = true;
                        this.notOnLineTimer.mark();
                    }
                    break;
                case RIGHT_OF_LINE:
                    if (!this.adjustingPosition) {
                        this.immediateStop();
                        this.turnLeft();
                        this.adjustingPosition = true;
                        this.notOnLineTimer.mark();
                    }
                    break;
                case JUST_LEFT_OF_LINE:
                    //This should be empty
                    break;
                case JUST_RIGHT_OF_LINE:
                    //This should be empty
                    break;
                case CROSSING:
                    if (this.crossRoadTimer.timeout()) {
                        // when turning left, right or rotating then the crossRoadTimer interval is set higher, this automatically resets it back
                        this.crossRoadTimer.setInterval(3000);
                        this.immediateStop();
                        this.notOnLineTimer.mark();
                        System.out.println("Crossing passed");

                        //If a route is being followed detect crossroads to determine the next step in the route.
                        if (this.isFollowingRoute()) {
                            this.routeNextStep();
                        }
                    }
                    break;
                default:
                    System.out.println("[DriveSystem] Error default onLineFollowCallback 1");
                    break;
            }
            //Adjusting position slightly while following the route
        } else if (this.followLine && this.adjustingPosition) {
            if (linePosition == LineFollower.LinePosition.ON_LINE) {
                System.out.println("Adjusted position to be on the line");
                this.immediateStop();
                this.adjustingPosition = false;
                this.setSpeed(this.followSpeed);
            } else if (linePosition == LineFollower.LinePosition.CROSSING) {
                this.adjustingPosition = false;
            }
             //Turning while following line so it turns to the next corner (usually a 90 degree turn).
        } else if (this.followLine && !this.drivingSlightlyForwardsBeforeTurningTimer.isOn()) {
            if (linePosition == LineFollower.LinePosition.LEFT_OF_LINE && this.turningRight) {
                System.out.println("Completed a turn to the right");
                this.immediateStop();
                this.turningRight = false;
            } else if (linePosition == LineFollower.LinePosition.RIGHT_OF_LINE && this.turningLeft) {
                System.out.println("Completed a turn to the left");
                this.immediateStop();
                this.turningLeft = false;
            } else if (linePosition == LineFollower.LinePosition.ON_LINE && this.rotateFully) {
                if (!this.isRotateFullyPartOneComplete) {
                    System.out.println("Completed part one of fully rotating");
                    this.immediateStop();
                    this.drivingSlightlyForwardsBeforeTurningTimer.setOn(true);
                    this.isRotateFullyPartOneComplete = true;
                } else {
                    System.out.println("Completed part two of rotating fully");
                    this.immediateStop();
                    this.rotateFully = false;
                }
            }

            // For turning at the end of a route, also needs to give back that the turn has been completed.
            // Followline will be false when this turn happens, so the above else if statement isn't reached.
            // Once the turn has been completed it stops and waits for a new timer to expire before following the new route.
        } else if (this.turnAtEnd /*&& !this.drivingSlightlyForwardsBeforeTurningTimer.isOn()*/) {
            if (linePosition == LineFollower.LinePosition.LEFT_OF_LINE) {
                // Turning can only happen in one direction, otherwise it will stop almost immediately.
                // For now it turns leftwards.
                System.out.println("Done turning at the end");
                this.hasTurnedAroundAtTheEndOfRoute = true;
                this.turnAtEnd = false;
                this.immediateStop();
                this.setFollowLine(true);
                this.followingRoute = true;
            }
        }
    }
}
