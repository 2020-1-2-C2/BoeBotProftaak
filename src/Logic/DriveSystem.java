package Logic;

import Hardware.DirectionalServo;
import Hardware.LineFollower;
import Hardware.ServoMotor;
import Utils.LineFollowCallback;
import Utils.Motor;
import Utils.TimerWithState;
import Utils.Updatable;

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
    private static final boolean RIGHT = true;
    private static final boolean LEFT = false;

    private boolean followingRoute = false;
    private Route route;
    private boolean turningLeft = false;
    private boolean turningRight = false;
    private boolean turnAtEnd = false;
    private TimerWithState routeTimer = new TimerWithState(2000, false);
    private TimerWithState turnAtEndTimer = new TimerWithState(500, false);

    public DriveSystem() {
        //TODO: Don't hardcode orientation like this.
        this.motor = new ServoMotor(new DirectionalServo(Configuration.servoMotor1PinId, 1), new DirectionalServo(Configuration.servoMotor2PinId, -1));
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

    public void turnLeft() {
        turn(LEFT, this.MAX_SPEED / this.STEPS * 2);
    }

    public void turnRight() {
        turn(RIGHT, this.MAX_SPEED / this.STEPS * 2);
    }

    /**
     * @param speed speed in percent.
     */
    void turnLeft(int speed) {
        turn(LEFT, speed);
    }

    /**
     * @param direction true = right, false = left.
     * @param speed     speed in percent.
     */
    private void turn(boolean direction, int speed) {
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
        this.followSpeed = 50;
        this.setDirection(FORWARD);
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
        this.debugToString();
        //TODO: if route resuming is implemented the resuming of the endturn needs to be implemented correctly, especially the reversing of the route needs to be implemented correctly.
        if (this.turnAtEndTimer.isOn() && this.turnAtEndTimer.timeout()) {
            // Stop driving backwards and start turning around.
            this.stop();
            this.turnLeft(50);
            this.turnAtEndTimer.setOn(false);
        } else if (this.routeTimer.isOn() && this.routeTimer.timeout()) {
            if (this.turnAtEnd) {
                // Start driving backwards when then end of a route is reached and set a timer to stop this.
                this.setSpeed(this.followSpeed);
                this.setDirection(BACKWARD);
                //TODO: notification for driving backwards?
                this.turnAtEndTimer.mark();
                this.turnAtEndTimer.setOn(true);
            } else {
                // When the BoeBot is turned around, reverse the route and follow the new route, back to the starting position.
                this.route.reverse();
                this.followRoute(this.route);
            }
            this.routeTimer.setOn(false);
        }
    }

    /**
     * Start following the provided route
     * @param route Route object to use
     */
    public void followRoute(Route route) {
        this.setFollowingRoute(true);
        this.route = route;
        this.followLine(true);
    }

    /**
     * Get the next step of the route and take the appropriate action to follow it.
     */
    private void routeNextStep() {
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
                // Start the process of turning around and following the route back.
                this.turnAtEnd = true;
                this.routeTimer.mark();
                this.routeTimer.setOn(true);
                break;
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
     * Set the value of following route.
     * @param followingRoute value to set
     */
    private void setFollowingRoute(boolean followingRoute) {
        this.followingRoute = followingRoute;
    }

    //TODO situation where the line stops, the robot should stop then
    @Override
    public void onLineFollow(LineFollower.LinePosition linePosition) {
        // Following the line normally while not turning
        if (this.followLine && !this.turningRight && !this.turningLeft) {
            switch (linePosition) {
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
                    // If a route is being followed detect crossroads to determine the next step in the route.
                    if (this.isFollowingRoute()) {
                        this.routeNextStep();
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
                case RIGHT_OF_LINE:
                    this.turnAtEnd = false;
                    this.stop();
                    this.routeTimer.mark();
                    this.routeTimer.setOn(true);
                    break;
                case LEFT_OF_LINE:
                    this.turnAtEnd = false;
                    this.stop();
                    this.routeTimer.mark();
                    this.routeTimer.setOn(true);
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
    private void debugToString(){
        if (this.route != null && this.route.nextDirection() != Route.NONE){
            for (int i : this.route.getRoute()){
                System.out.println("Next move: " + i);
            }
            System.out.println("\n");
        }
    }

}
