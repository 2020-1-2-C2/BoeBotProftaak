package Logic;

import Hardware.Motors;
import Utils.Updatable;

public class DriveSystem implements Updatable {
    Motors motors;
    private static final int stopSpeed = 1500;

    public DriveSystem(Motors motors) {
        this.motors = motors;
    }

    public void emergencyStop(int distance) {

        //TODO use speed and distance to calculate time for stopping
        motors.emergencyBrake();

    }

    @Override
    public void update() {

    }
}
