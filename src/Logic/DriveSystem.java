package Logic;

import Hardware.LineFollower;
import Utils.LineFollowCallback;
import Utils.Motor;
import Utils.TimerWithState;
import Utils.Updatable;


//TODO: Remove unnecessary comments & code
//TODO: Update documentation

public class DriveSystem implements Updatable, LineFollowCallback {
    private Motor motor;
    private final int STEPS = 10;
    private final int MAX_SPEED = 100;
    private int currentMaxSpeed = MAX_SPEED;
    // time in ms
    private final int ACCELERATION_TIME = 500;
    private int currentSpeed = 0;
    private int currentSpeedRight = 0;
    private int currentSpeedLeft = 0;
    private int direction = 1;
    private boolean followLine = false;
    private int followSpeed;

    public static final int FORWARD = 1;
    public static final int BACKWARD = -1;
    private static final boolean RIGHT = true;
    private static final boolean LEFT = false;

    private boolean followingRoute = false;
    private Route route;
    private boolean turningLeft = false;
    private boolean turningRight = false;
    private boolean turnAtEnd = false;
    private TimerWithState routeTimer = new TimerWithState(2000, false);
    private TimerWithState turnAtEndTimer = new TimerWithState(500, false);

    public DriveSystem(Motor motors) {
        this.motor = motors;
    }

    /**
     * @param speed value between 0 and 100
     */
    public void setSpeed(int speed) {

        if (speed > currentMaxSpeed) {
            speed = currentMaxSpeed;
        } else if (speed < 0) {
            speed = 0;
        }
        speed = speed * direction;
        currentSpeed = speed;
        currentSpeedLeft = currentSpeed;
        currentSpeedRight = currentSpeed;

        motor.goToSpeed(speed, ACCELERATION_TIME);
    }

    /**
     * @param direction 1 is forward, -1 is backward
     */
    public void setDirection(int direction) {
        if (direction != this.direction && (direction == FORWARD || direction == BACKWARD)) {
            this.direction = direction;
            setSpeed(MAX_SPEED / STEPS);
        }
    }

    public void turnLeft() {
        turn(LEFT, MAX_SPEED / STEPS * 2);
    }

    public void turnRight() {
        turn(RIGHT, MAX_SPEED / STEPS * 2);
    }

    /**
     * @param speed speed in percent
     */
    public void turnLeft(int speed) {
        turn(LEFT, speed);
    }

    /**
     * @param speed speed in percent
     */
    public void turnRight(int speed) {
        turn(RIGHT, speed);
    }

    /**
     * @param direction true = right, false = left
     * @param speed     speed in percent
     */
    private void turn(boolean direction, int speed) {
        if (direction) {
            currentSpeedRight = currentSpeed - speed / 2;
            currentSpeedLeft = currentSpeed + speed / 2;
            if (currentSpeedLeft > 100) {
                currentSpeedLeft = 100;
                currentSpeedRight = 100 - speed;
            }
        } else {
            currentSpeedRight = currentSpeed + speed / 2;
            currentSpeedLeft = currentSpeed - speed / 2;
            if (currentSpeedRight > 100) {
                currentSpeedRight = 100;
                currentSpeedLeft = 100 - speed;
            }
        }

        motor.goToSpeedRight(currentSpeedRight * this.direction, ACCELERATION_TIME);
        motor.goToSpeedLeft(currentSpeedLeft * this.direction, ACCELERATION_TIME);

    }

    /**
     * Gradually stop BoeBot.
     */
    public void stop() {
        currentSpeed = 0;
        currentSpeedLeft = currentSpeed;
        currentSpeedRight = currentSpeed;
        motor.goToSpeed(0, this.ACCELERATION_TIME);
    }

    /**
     * Immediately stop BoeBot
     */
    public void emergencyStop() {
        currentSpeed = 0;
        currentSpeedLeft = currentSpeed;
        currentSpeedRight = currentSpeed;
        motor.emergencyStop();
    }

    /**
     * @param follow if true the BoeBot will start line following functionality
     */
    public void followLine(boolean follow) {
        followLine(follow, 50);
    }

    /**
     * @param follow      if true the BoeBot will start line following functionality
     * @param followSpeed the speed of the BoeBot while following the line.
     */
    public void followLine(boolean follow, int followSpeed) {
        this.followLine = follow;
        this.followSpeed = followSpeed;
        setDirection(FORWARD);
    }

    /**
     * @return current maximum speed.
     */
    public int getCurrentMaxSpeed() {
        return currentMaxSpeed;
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
        return currentSpeed;
    }

    @Override
    public void update() {
        //TODO if route resuming is implemented the resuming of the endturn needs to be implemented correctly, especially the reversing of the route needs to be implemented correctly
        if (turnAtEndTimer.isOn() && turnAtEndTimer.timeout()) {
            // stop driving backwards and start turning around
            stop();
            turnLeft(50);
            turnAtEndTimer.setOn(false);
        } else if (routeTimer.isOn() && routeTimer.timeout()) {
            if (turnAtEnd) {
                // start driving backwards when then end of a route is reached and set a timer to stop this
                //TODO notification for driving backwards?
                setSpeed(followSpeed);
                setDirection(BACKWARD);
                turnAtEndTimer.mark();
                turnAtEndTimer.setOn(true);
            } else {
                // when the BoeBot is turned around, reverse the route and follow the new route, back to the starting position
                route.reverse();
                followRoute(route);
            }
            routeTimer.setOn(false);
        }
    }

    /**
     * Auto-generated getter for the variable direction
     *
     * @return An int representing the direction the bot is heading to
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Start following the provided route
     *
     * @param route Route object to use
     */
    public void followRoute(Route route) {
        setFollowingRoute(true);
        this.route = route;
        followLine(true);
    }

    /**
     * Get the next step of the route and take the appropriate action to follow it.
     */
    private void routeNextStep() {
        switch (route.nextDirection()) {
            case Route.FORWARD:
                this.setSpeed(followSpeed);
                break;
            case Route.LEFT:
                turningLeft = true;
                turnLeft();
                break;
            case Route.RIGHT:
                turningRight = true;
                turnRight();
                break;
            case Route.NONE:
                followLine(false);
                setFollowingRoute(false);
                this.stop();
                // Start the process of turning around and following the route back.
                turnAtEnd = true;
                routeTimer.mark();
                routeTimer.setOn(true);
                break;
            default:
                // If there is no valid instruction stop following the route.
                //TODO notificatie dat er geen route is
                followLine(false);
                setFollowingRoute(false);
                this.stop();
                break;
        }
    }

    /**
     * @return value of followingRoute
     */
    public boolean isFollowingRoute() {
        return followingRoute;
    }

    /**
     * Stop following the route.
     */
    public void stopFollowingRoute() {
        setFollowingRoute(false);
        turnAtEndTimer.setOn(false);
        routeTimer.setOn(false);
    }

    /**
     * Set the value of following route.
     *
     * @param followingRoute value to set
     */
    private void setFollowingRoute(boolean followingRoute) {
        this.followingRoute = followingRoute;
    }

    @Override
    public void onLineFollow(LineFollower.LinePosition linePosition) {
        // Following the line normally while not turning
        if (this.followLine && !turningRight && !turningLeft) {
            switch (linePosition) {
                case ON_LINE:
                    this.setSpeed(followSpeed);
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
                    // if a route is being followed detect crossroads to determine the next step in the route
                    if (isFollowingRoute()) {
                        routeNextStep();
                    }
            }
            // Turning while following line so it turns to the next corner (usually a 90 degree turn)
        } else if (this.followLine && (turningLeft || turningRight)) {
            switch (linePosition) {
                case LEFT_OF_LINE:
                    if (turningRight) {
                        turningRight = false;
                    }
                    break;
                case RIGHT_OF_LINE:
                    if (turningLeft) {
                        turningLeft = false;
                    }
                    break;
            }
            // For turning at the end of a route, also needs to give back that the turn has been completed
            // Followline will be false when this turn happens, so the above else if statement isn't reached
            // Once the turn has been completed it stops and waits for a new timer to expire before following the new route
        } else if (turnAtEnd) {
            switch (linePosition) {
                case RIGHT_OF_LINE:
                    turnAtEnd = false;
                    stop();
                    routeTimer.mark();
                    routeTimer.setOn(true);
                    break;
                case LEFT_OF_LINE:
                    turnAtEnd = false;
                    stop();
                    routeTimer.mark();
                    routeTimer.setOn(true);
                    break;
            }
        }
    }
}
