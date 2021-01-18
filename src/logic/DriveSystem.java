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
    private final int STEPS = 10;
    private final int MAX_SPEED = 100;
    private int currentMaxSpeed = MAX_SPEED;
    // Time in ms.
    private final int ACCELERATION_TIME = 500;
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
    private boolean turnAtEnd = false;
    private TimerWithState routeTimer = new TimerWithState(10000, false);
    private TimerWithState routeWaitTimer = new TimerWithState(2000, false);
    private TimerWithState turnAtEndTimer = new TimerWithState(1000, false);
    private TimerWithState crossRoadTimer = new TimerWithState(1000, true);
    private boolean hasTurnedAroundAtTheEndOfRoute = false;
    private boolean getReadyForNextRoute = false;

    private boolean ridingUntillTheNextCrossroad = false;

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

        this.motor.goToSpeed(speed, this.ACCELERATION_TIME);
    }

    /**
     * @param direction 1 is forward, -1 is backward
     */
    public void setDirection(int direction) {
        if (direction != this.direction && (direction == FORWARD || direction == BACKWARD)) {
            this.direction = direction;
            setSpeed(this.MAX_SPEED / this.STEPS);
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
        turn(LEFT, this.MAX_SPEED / this.STEPS);
    }

    /**
     * Turn right at the lowest speed.
     */
    public void turnRight() {
        turn(RIGHT, this.MAX_SPEED / this.STEPS);
    }

    /**
     * @param direction true = right, false = left.
     * @param speed     speed in percent.
     */
    public void turn(boolean direction, int speed) {
        if (direction) {
            this.currentSpeedRight = this.currentSpeed - speed / 2;
            this.currentSpeedLeft = this.currentSpeed + speed / 2;
            if (this.currentSpeedLeft > 100) {
                this.currentSpeedLeft = 100;
                this.currentSpeedRight = 100 - speed;
            }
        } else {
            this.currentSpeedRight = this.currentSpeed + speed / 2;
            this.currentSpeedLeft = this.currentSpeed - speed / 2;
            if (this.currentSpeedRight > 100) {
                this.currentSpeedRight = 100;
                this.currentSpeedLeft = 100 - speed;
            }
        }

        this.motor.goToSpeedRight(this.currentSpeedRight * this.direction, this.ACCELERATION_TIME);
        this.motor.goToSpeedLeft(this.currentSpeedLeft * this.direction, this.ACCELERATION_TIME);
    }

    /**
     * Gradually stops the BoeBot.
     */
    public void stop() {
        this.currentSpeed = 0;
        this.currentSpeedLeft = this.currentSpeed;
        this.currentSpeedRight = this.currentSpeed;
        this.motor.goToSpeed(0, this.ACCELERATION_TIME);
    }

    /**
     * Immediately stops the BoeBot.
     */
    public void emergencyStop() {
        this.currentSpeed = 0;
        this.currentSpeedLeft = this.currentSpeed;
        this.currentSpeedRight = this.currentSpeed;
        this.motor.emergencyStop();
    }

    /**
     * @param follow if true the BoeBot will start line following functionality
     */
    public void followLine(boolean follow) {
        this.followLine = follow;
        System.out.println("Volgt nu route");
        this.followSpeed = 25;
        setDirection(FORWARD);
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
        if (currentMaxSpeed > this.MAX_SPEED) {
            currentMaxSpeed = this.MAX_SPEED;
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

//        this.debugToString();
        //TODO: if route resuming is implemented the resuming of the endturn needs to be implemented correctly, especially the reversing of the route needs to be implemented correctly.
        if (!this.hasTurnedAroundAtTheEndOfRoute) {
            if (this.turnAtEndTimer.isOn() && this.turnAtEndTimer.timeout()) {
                // Stop driving backwards and start turning around.
                System.out.println("Stop driving backwards and start turning around.");
                this.stop();
                // always turn to the left
                this.setDirection(FORWARD);
                this.turn(LEFT, 50);
                this.turnAtEndTimer.setOn(false);
            } else if (this.turnAtEnd && this.routeTimer.isOn() && this.routeTimer.timeout()) {
                // Start driving backwards at a minimal speed when then end of a route is reached and set a timer to stop this.
                System.out.println("Start driving backwards at a minimal speed when then end of a route is reached and set a timer to stop this.");
                this.setSpeed(10);
                this.setDirection(BACKWARD);
                this.turnAtEndTimer.mark();
                this.turnAtEndTimer.setOn(true);
                this.routeTimer.setOn(false);
            } else if (routeWaitTimer.isOn() && routeWaitTimer.timeout()) {
                // When the BoeBot is turned around and has waited 10 seconds, follow the new reversed route back to the starting position.
                System.out.println("When the BoeBot is turned around and has waited 10 seconds, follow the new reversed route back to the starting position.");
                this.followLine(true);
                this.routeWaitTimer.setOn(false);
                this.followRoute(this.route);
                this.hasTurnedAroundAtTheEndOfRoute = true;
            }
        }
        // logic for getting ready for the next route when the previous has finished
        if (this.hasTurnedAroundAtTheEndOfRoute && this.getReadyForNextRoute) {
            if (this.turnAtEndTimer.isOn() && this.turnAtEndTimer.timeout()) {
                // Stop driving forwards and start turning around.
                System.out.println("Stop driving forwards and start turning around.");
                this.stop();
                // always turn to the left
                this.turn(LEFT, 50);
                this.turnAtEndTimer.setOn(false);
            } else if (this.turnAtEnd && this.routeTimer.isOn() && this.routeTimer.timeout()) {
                // Start driving forwards slowly to pass the crossroad.
                System.out.println("Start driving forwards slowly to pass the crossroad.");
                this.setSpeed(10);
                this.setDirection(FORWARD);
                this.turnAtEndTimer.mark();
                this.turnAtEndTimer.setOn(true);
                this.routeTimer.setOn(false);
            } else if (routeWaitTimer.isOn()) {
                // When the bot has turned around it can then stop and wait for further instructions, doesn't have to wait the 10 seconds here
                System.out.println("RIP RIP RIP RIP");
                this.routeWaitTimer.setOn(false);
                this.hasTurnedAroundAtTheEndOfRoute = false;
                this.getReadyForNextRoute = false;
                // Follow the line untill the next crossroad, then stop
                this.followingRoute = true;
                this.ridingUntillTheNextCrossroad = true;
            }
        }
    }

    /**
     * Start following the provided route.
     *
     * @param route Route object to use
     */
    public void followRoute(Route route) {
        System.out.println("FollowRoute: " + route.getRoute());
        this.setFollowingRoute(true);
        this.route = route;
        this.followLine(true);
    }

    /**
     * Get the next step of the route and take the appropriate action to follow it.
     */
    private void routeNextStep() {
        System.out.println("Next direction: " + this.route.nextDirection());
        switch (this.route.nextDirection()) {
            case Route.FORWARD:
                this.setSpeed(this.followSpeed);
                break;
            case Route.LEFT:
                this.turningLeft = true;
                this.turnLeft();
                break;
            case Route.RIGHT:
                this.turningRight = true;
                this.turnRight();
                break;
            case Route.NONE:
                this.followLine(false);
                this.setFollowingRoute(false);
                this.stop();
                this.turnAtEnd = true;
                this.routeTimer.mark();
                this.routeTimer.setOn(true);
                if (!this.hasTurnedAroundAtTheEndOfRoute) {
                    this.route.reverse();
                    break;
                } else {
                    this.getReadyForNextRoute = true;
                    break;
                }
            case Route.DESTINATION:
                this.followLine(false);
                this.stop();
                this.routeWaitTimer.mark();
                this.routeWaitTimer.setOn(true);
            default:
                // If there are no valid instructions, stop following the route.
                //TODO: Set the notification MissingRouteNotification.
                this.followLine(false);
                this.setFollowingRoute(false);
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
        this.turnAtEndTimer.setOn(false);
        this.routeTimer.setOn(false);
    }


    /**
     * Resume following of route after it is interrupted.
     */
    public void resumeRoute() {
        if (this.route != null) {
            followRoute(this.route);
        }
    }

    /**
     * Set the value of following route.
     * @param followingRoute value to set
     */
    private void setFollowingRoute(boolean followingRoute) {
        this.followingRoute = followingRoute;
    }

    @Override
    public void onLineFollow(LineFollower.LinePosition linePosition) {
        // Following the line normally while not turning
        if (this.followLine && !this.turningRight && !this.turningLeft) {
            switch (linePosition) {
                case NOT_ON_LINE:
                    /*
                    this.followLine(false);
                    this.stop();
                    */
                case ON_LINE:
                    this.setSpeed(this.followSpeed);
                    break;
                case LEFT_OF_LINE:
                    this.stop();
                    this.turnRight();
                    break;
                case RIGHT_OF_LINE:
                    this.stop();
                    this.turnLeft();
                    break;
                case JUST_LEFT_OF_LINE:
                    this.turnRight();
                    break;
                case JUST_RIGHT_OF_LINE:
                    this.turnLeft();
                    break;
                case CROSSING:
                    this.stop();
                    if (this.ridingUntillTheNextCrossroad) {
                        this.followingRoute = false;
                    }
                    // If a route is being followed detect crossroads to determine the next step in the route.
                    if (this.isFollowingRoute() && this.crossRoadTimer.timeout()) {
                        this.routeNextStep();
                        this.crossRoadTimer.mark();
                    }
            }
            // Turning while following line so it turns to the next corner (usually a 90 degree turn).
        } else if (this.followLine) {
            switch (linePosition) {
                case LEFT_OF_LINE:
                    if (this.turningRight) {
                        this.turningRight = false;
                    }
                    break;
                case RIGHT_OF_LINE:
                    if (this.turningLeft) {
                        this.turningLeft = false;
                    }
                    break;
            }
            // For turning at the end of a route, also needs to give back that the turn has been completed.
            // Followline will be false when this turn happens, so the above else if statement isn't reached.
            // Once the turn has been completed it stops and waits for a new timer to expire before following the new route.
        } else if (this.turnAtEnd) {
            switch (linePosition) {
                // Turning can only happen in one direction, otherwise it will stop almost immediately.
                // For now it turns leftwards.
                case RIGHT_OF_LINE:
                    this.turnAtEnd = false;
                    this.stop();
                    this.routeWaitTimer.mark();
                    this.routeWaitTimer.setOn(true);
                    break;
            }
        }
    }

    //TODO: Remove on final release.
    /**
     * For debugging purposes only.
     * Called in <code>update()</code> to print out useful information.
     * @see #update()
     */
    private void debugToString() {
        if (this.route != null && this.route.nextDirection() != Route.NONE) {
            for (int i : this.route.getRoute()) {
                System.out.println("Next move: " + i);
            }
            System.out.println("\n");
        }
    }

}
