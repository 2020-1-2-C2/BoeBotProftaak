package Logic;

import Hardware.Motors;
import Utils.Updatable;

public class DriveSystem implements Updatable {
    private Motors motors;
    private static final int STOP_SPEED = 1500;
    private static final int MAX_FORWARD_SPEED = 1700;
    private static final int MAX_BACKWARD_SPEED = 1300;
    private static final int STEPS = 10;
    private int currentSpeed;

    public DriveSystem(Motors motors) {
        this.motors = motors;
    }

    public void addForwardSpeed(){
        int speedStep = (MAX_FORWARD_SPEED - STOP_SPEED) / STEPS;
        currentSpeed = ((motors.getSpeedLeft() + motors.getSpeedRight()) / 2) + speedStep;
        if (currentSpeed > MAX_FORWARD_SPEED){
            currentSpeed = MAX_FORWARD_SPEED;
        }
        motors.goToSpeed(currentSpeed, 5);
    }

    public void addBackwardSpeed(){
        int speedStep = (MAX_BACKWARD_SPEED - STOP_SPEED) / STEPS;
        currentSpeed = ((motors.getSpeedLeft() + motors.getSpeedRight()) / 2) - speedStep;
        if (currentSpeed > MAX_BACKWARD_SPEED){
            currentSpeed = MAX_BACKWARD_SPEED;
        }
        motors.goToSpeed(currentSpeed, 5);
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


    }

    public void stop(){
        motors.goToSpeed(STOP_SPEED, 5);
    }

    public void emergencyStop(int distance) {

        //TODO use speed and distance to calculate time for stopping
        motors.emergencyBrake();

    }

    @Override
    public void update() {

    }
}
