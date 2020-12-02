package Logic;

import Utils.Motor;
import Utils.Updatable;

public class DriveSystem implements Updatable {
    private Motor motor;
    private final int STEPS = 10;
    private final int MAX_SPEED = 100;
    // time in ms
    private final int ACCELERATION_TIME = 500;
    private int currentSpeed;

    public DriveSystem(Motor motors) {
        this.motor = motors;
    }

    public void addForwardSpeed(){
        int newSpeed = ((motor.getSpeedLeft() + motor.getSpeedRight()) / 2) + MAX_SPEED/STEPS;
        motor.goToSpeed(MAX_SPEED, ACCELERATION_TIME);
    }

    public void addBackwardSpeed(){
        int newSpeed = ((motor.getSpeedLeft() + motor.getSpeedRight()) / 2) - MAX_SPEED/STEPS;
        motor.goToSpeed(MAX_SPEED, ACCELERATION_TIME);
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
        motor.goToSpeed(0, this.ACCELERATION_TIME);
    }

    public void emergencyStop() {
        motor.emergencyStop();
    }

    @Override
    public void update() {

    }
}
