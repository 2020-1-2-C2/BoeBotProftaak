import Hardware.InfraredReceiver;
import Hardware.Motors;
import Hardware.UltraSonicReceiver;
import Logic.CollisionDetection;
import Logic.DriveSystem;
import TI.BoeBot;
import TI.PinMode;
import Utils.CollisionDetectionCallback;
import Utils.InfraredCallback;
import Utils.UltraSonicCallback;
import Utils.Updatable;

import java.util.ArrayList;

public class RobotMain implements InfraredCallback, CollisionDetectionCallback {

    private ArrayList<Updatable> updatables = new ArrayList<>();
    private DriveSystem driveSystem;

    public static void main(String[] args) {

        RobotMain main = new RobotMain();
        main.run();

    }


    /**
     * Main run method for the boebot logic.
     */
    public void run() {
        BoeBot.setMode(0, PinMode.Input);
        InfraredReceiver infraredReceiver = new InfraredReceiver(0, this);

        CollisionDetection collisionDetection = new CollisionDetection(this);
        UltraSonicReceiver ultraSonicReceiver = new UltraSonicReceiver(1, 2, collisionDetection);

        Motors motors = new Motors(5);
        driveSystem = new DriveSystem(motors);


        updatables.add(infraredReceiver);
        updatables.add(ultraSonicReceiver);
        updatables.add(collisionDetection);
        updatables.add(driveSystem);

        while (true) {
            for (Updatable u : updatables) {
                u.update();
            }
            BoeBot.wait(10);
        }
    }

    /**
     * Receives the pressed button on an infrared remote and prints it.
     * @param button received button pressed on the infrared remote.
     */
    @Override
    public void OnInfraredButton(String button) {
        // TODO, code can be a null object with wrong or unrecognised measurements, this needs to be taken into account.
        System.out.println(button);
    }

    @Override
    public void onCollisionDetection(int distance) {
        driveSystem.emergencyStop(distance);
    }
}