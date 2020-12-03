import Hardware.*;
import Logic.CollisionDetection;
import Logic.DriveSystem;
import Logic.Notifications;
import TI.BoeBot;
import TI.PinMode;
import TI.Servo;
import Utils.*;

import java.awt.*;
import java.util.ArrayList;

public class RobotMain implements InfraredCallback, CollisionDetectionCallback {

    private ArrayList<Updatable> updatables = new ArrayList<>();
    private DriveSystem driveSystem;
    private Notifications notifications;
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

        Motor servoMotor = new ServoMotor(new Servo(12), new Servo(13));
        driveSystem = new DriveSystem(servoMotor);

        Buzzer buzzer = new Buzzer(6);
        ArrayList<Buzzer> buzzers = new ArrayList<>();
        buzzers.add(buzzer);
        RGBLed rgbLed = new RGBLed(5, 4, 3, Color.black);
        ArrayList<Led> leds = new ArrayList<>();
        leds.add(rgbLed);
        notifications = new Notifications(buzzers, leds);


        updatables.add(infraredReceiver);
        updatables.add(ultraSonicReceiver);
        updatables.add(collisionDetection);
        updatables.add(driveSystem);
        updatables.add(buzzer);
        updatables.add(rgbLed);
        updatables.add(servoMotor);

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
            notifications.remoteNotification();
            switch (button) {
                case "power":
                    driveSystem.stop();
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
                case "1":
                    break;
            }
        }
        System.out.println(button);
    }

    @Override
    public void onCollisionDetection(int distance) {
        driveSystem.emergencyStop();
        //notifications.emergencyNotification();
    }
}