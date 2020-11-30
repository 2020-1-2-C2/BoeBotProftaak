package Logic;

import Hardware.Motors;
import Utils.Updatable;

public class DriveSystem implements Updatable {
    Motors motors;
    private static final int STOP_SPEED = 1500;
    private static final int MAX_FORWARD_SPEED = 1700;
    private static final int MAX_BACKWARD_SPEED = 1300;
    private static final int STEPS = 10;
    private int currentSpeed = 1500;

    public DriveSystem(Motors motors) {
        this.motors = motors;
    }

    public void addForwardSpeed(){
        int speedStep = (MAX_FORWARD_SPEED - STOP_SPEED) / STEPS;
        currentSpeed = ((motors.getSpeedLeft() + motors.getSpeedRight()) / 2) + speedStep;
        if (currentSpeed > 1700){
            currentSpeed = 1700;
        }
        motors.goToSpeed(currentSpeed, 5);
    }

    public void addBackwardSpeed(){
        int speedStep = (MAX_FORWARD_SPEED - STOP_SPEED) / STEPS;
        currentSpeed = ((motors.getSpeedLeft() + motors.getSpeedRight()) / 2) - speedStep;
        if (currentSpeed > 1300){
            currentSpeed = 1300;
        }
        motors.goToSpeed(currentSpeed, 5);
    }

    public void stop(){
        motors.goToSpeed(1500, 5);
    }

    public void emergencyStop(int distance) {

        //TODO use speed and distance to calculate time for stopping
        motors.emergencyBrake();

    }

    @Override
    public void update() {

    }
}
