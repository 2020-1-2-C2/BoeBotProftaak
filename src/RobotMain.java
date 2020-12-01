import Hardware.*;
import Logic.CollisionDetection;
import Logic.DriveSystem;
import Logic.Notifications;
import TI.BoeBot;
import TI.PinMode;
import Utils.CollisionDetectionCallback;
import Utils.InfraredCallback;
import Utils.Updatable;

import java.util.ArrayList;

public class RobotMain implements InfraredCallback, CollisionDetectionCallback {

    private ArrayList<Updatable> updatables = new ArrayList<>();
    private DriveSystem driveSystem;
    private boolean running = true;

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

        Buzzer buzzer = new Buzzer(6);
        ArrayList<Buzzer> buzzers = new ArrayList<>();
        buzzers.add(buzzer);
        ArrayList<Led> leds = new ArrayList<>();
        Notifications notifications = new Notifications(buzzers, leds);


        updatables.add(infraredReceiver);
        updatables.add(ultraSonicReceiver);
        updatables.add(collisionDetection);
        updatables.add(driveSystem);
        updatables.add(buzzer);

        while (running) {
            for (Updatable u : updatables) {
                u.update();
            }
            BoeBot.wait(10);
        }
    }

    /**
     * Receives the pressed button on an infrared remote and prints it.
     *
     * @param button received button pressed on the infrared remote.
     */
    @Override
    public void OnInfraredButton(String button) {
        if (button != null) {
            switch (button) {
                case "power":
                    driveSystem.stop();
                    running = false;
                    break;
                case "ch+":
                    driveSystem.addForwardSpeed();
                    break;
                case "ch-":
                    driveSystem.addBackwardSpeed();
                    break;
                case "vol+":
                    driveSystem.turnRight();
                    break;
                case "vol-":
                    driveSystem.turnLeft();
                    break;
            }
        }
        System.out.println(button);
    }

    @Override
    public void onCollisionDetection(int distance) {
        driveSystem.emergencyStop(distance);
    }
}