package Logic;

import Utils.Motor;
import Utils.Updatable;

public class DriveSystem implements Updatable {
    private Motor motor;
    private final int STEPS = 10;
    private final int MAX_SPEED = 100;
    // time in ms
    private final int ACCELERATION_TIME = 500;
    private int currentSpeed = 0;
    private int currentSpeedRight = 0;
    private int currentSpeedLeft = 0;

    public DriveSystem(Motor motors) {
        this.motor = motors;
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
        int newSpeedRight;
        int newSpeedLeft;

        if (direction) {
            currentSpeedRight = currentSpeedRight - diff;
            currentSpeedLeft = currentSpeedLeft + diff;
            if (currentSpeedLeft > 100) {
                currentSpeedLeft = 100;
                currentSpeedRight = 80;
            } else if (currentSpeedLeft < -100) {
                currentSpeedLeft = -100;
                currentSpeedRight = -80;
            }
        } else {
            currentSpeedRight = currentSpeedRight + diff;
            currentSpeedLeft = currentSpeedLeft - diff;
            if (currentSpeedRight > 100) {
                currentSpeedRight = 100;
                currentSpeedLeft = 80;
            } else if (currentSpeedRight < -100) {
                currentSpeedRight = -100;
                currentSpeedLeft = -80;
            }
        }
        currentSpeed = (currentSpeedLeft + currentSpeedRight) / 2;

        motor.goToSpeedRight(currentSpeedRight, ACCELERATION_TIME);
        motor.goToSpeedLeft(currentSpeedLeft, ACCELERATION_TIME);



        /*
        stop();
        int speedStep = (MAX_FORWARD_SPEED - STOP_SPEED) / STEPS;
        int maxSpeedRight;
        int maxSpeedLeft;
        int currentRightSpeed;
        int currentLeftSpeed;

        if (direction) {
            currentRightSpeed = motors.getSpeedRight() - speedStep;
            currentLeftSpeed = motors.getSpeedLeft() + speedStep;
            maxSpeedRight = MAX_BACKWARD_SPEED;
            maxSpeedLeft = MAX_FORWARD_SPEED;
        } else {
            currentRightSpeed = motors.getSpeedRight() + speedStep;
            currentLeftSpeed = motors.getSpeedLeft() - speedStep;
            maxSpeedRight = MAX_FORWARD_SPEED;
            maxSpeedLeft = MAX_BACKWARD_SPEED;
        }

        if (currentRightSpeed > maxSpeedRight) {
            currentRightSpeed = maxSpeedRight;
        }
        if (currentLeftSpeed > maxSpeedLeft) {
            currentLeftSpeed = maxSpeedLeft;
        }

        motors.goToSpeedRight(currentRightSpeed, 5);
        motors.goToSpeedLeft(currentLeftSpeed, 5);
        */


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

    @Override
    public void update() {

    }
}
