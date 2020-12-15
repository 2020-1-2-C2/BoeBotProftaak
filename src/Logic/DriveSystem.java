package Logic;

import Hardware.LineFollower;
import Utils.LineFollowCallback;
import Utils.Motor;
import Utils.Updatable;

public class DriveSystem implements Updatable, LineFollowCallback {
    private Motor motor;
    private final int STEPS = 10;
    private final int MAX_SPEED = 100;
    // time in ms
    private final int ACCELERATION_TIME = 500;
    private int currentSpeed = 0;
    private int currentSpeedRight = 0;
    private int currentSpeedLeft = 0;
    private int direction = 1;
    private boolean followLine = false;
    private int followSpeed;

    public DriveSystem(Motor motors) {
        this.motor = motors;
    }

    /**
     *
     * @param speed value between 0 and 100
     */
    public void setSpeed(int speed) {

        if (speed > 100) {
            speed = 100;
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
     *
     * @param direction 1 is forward, -1 is backward
     */
    public void setDirection(int direction) {
        if (direction != this.direction && (direction == 1 || direction == -1)) {
            this.direction = direction;
            setSpeed(MAX_SPEED/STEPS);
        }
    }



    public void addForwardSpeed(){
        addSpeed(true);
    }

    public void addBackwardSpeed(){
        addSpeed(false);
    }

    /**
     *
     * @param direction true = forwards, false = backwards
     */
    public void addSpeed(boolean direction) {
        int speedDiff;
        if (direction) {
            speedDiff = MAX_SPEED/STEPS;
        } else {
            speedDiff = - MAX_SPEED/STEPS;
        }
        currentSpeed = currentSpeed + speedDiff;

        if (currentSpeed > 100) {
            currentSpeed = 100;
        } else if (currentSpeed < -100) {
            currentSpeed = -100;
        }

        currentSpeedRight = currentSpeed;
        currentSpeedLeft = currentSpeed;

        System.out.println(motor.getSpeedLeft());
        System.out.println(motor.getSpeedRight());
        System.out.println(MAX_SPEED/STEPS);
        motor.goToSpeed(currentSpeed, ACCELERATION_TIME);

    }

    public void turnLeft() {
        turn(false);
    }

    public void turnRight() {
        turn(true);
    }

    /**
     *
     * @param direction true = right, false = left
     */
    private void turn(boolean direction) {

        int diff = MAX_SPEED/STEPS;


        if (direction) {
            currentSpeedRight = currentSpeed - diff;
            currentSpeedLeft = currentSpeed + diff;
            if (currentSpeedLeft > 100) {
                currentSpeedLeft = 100;
                currentSpeedRight = 80;
            }
        } else {
            currentSpeedRight = currentSpeed + diff;
            currentSpeedLeft = currentSpeed - diff;
            if (currentSpeedRight > 100) {
                currentSpeedRight = 100;
                currentSpeedLeft = 80;
            }
        }

        motor.goToSpeedRight(currentSpeedRight * this.direction, ACCELERATION_TIME);
        motor.goToSpeedLeft(currentSpeedLeft * this.direction, ACCELERATION_TIME);

    }

    public void stop(){
        currentSpeed = 0;
        currentSpeedLeft = currentSpeed;
        currentSpeedRight = currentSpeed;
        motor.goToSpeed(0, this.ACCELERATION_TIME);
        //motor.emergencyStop();
    }

    public void emergencyStop() {
        currentSpeed = 0;
        currentSpeedLeft = currentSpeed;
        currentSpeedRight = currentSpeed;
        motor.emergencyStop();
    }

    public void followLine(boolean follow) {
        followLine(follow, 50);
    }
    
    public void followLine(boolean follow, int followSpeed) {
        this.followLine = follow;
        this.followSpeed = followSpeed;
    }

    @Override
    public void update() {

    }

    /**
     * Auto-generated getter for the variable direction
     * @return An int representing the direction the bot is heading to
     */
    public int getDirection() {
        return direction;
    }

    @Override
    public void onLineFollow(LineFollower.LinePosition linePosition) {
        if (this.followLine) {
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
            }
        }
    }
}
