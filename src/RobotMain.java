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

        Motor servoMotor = new ServoMotor(new DirectionalServo(12, 1), new DirectionalServo(13, -1));
        driveSystem = new DriveSystem(servoMotor);

        Buzzer buzzer = new Buzzer(6);
        ArrayList<Buzzer> buzzers = new ArrayList<>();
        buzzers.add(buzzer);
//        RGBLed rgbLed = new RGBLed(5, 4, 3, Color.black);
//        ArrayList<Led> leds = new ArrayList<>();
//        leds.add(rgbLed);
        NeoPixelLed neoPixelLed = new NeoPixelLed(0);
        ArrayList<NeoPixelLed> neoPixelLeds = new ArrayList<>();
        neoPixelLeds.add(neoPixelLed);
        notifications = new Notifications(buzzers, neoPixelLeds);


        updatables.add(infraredReceiver);
        updatables.add(ultraSonicReceiver);
        updatables.add(collisionDetection);
        updatables.add(driveSystem);
        updatables.add(buzzer);
        updatables.add(neoPixelLed);
        updatables.add(servoMotor);

        testSong();

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
    public void onInfraredButton(String button) {
        if (button != null) {
            notifications.remoteNotification();
            switch (button) {
                case "power":
                    driveSystem.stop();
                    break;
                case "ch+":
                    driveSystem.setDirection(1);
                    break;
                case "ch-":
                    driveSystem.setDirection(-1);
                    break;
                case "vol+":
                    driveSystem.turnRight();
                    break;
                case "vol-":
                    driveSystem.turnLeft();
                    break;
                case "1":
                    driveSystem.setSpeed(10);
                    break;
                case "2":
                    driveSystem.setSpeed(20);
                    break;
                case "3":
                    driveSystem.setSpeed(30);
                    break;
                case "4":
                    driveSystem.setSpeed(40);
                    break;
                case "5":
                    driveSystem.setSpeed(50);
                    break;
                case "6":
                    driveSystem.setSpeed(60);
                    break;
                case "7":
                    driveSystem.setSpeed(70);
                    break;
                case "8":
                    driveSystem.setSpeed(80);
                    break;
                case "9":
                    driveSystem.setSpeed(90);
                    break;
                case "0":
                    driveSystem.setSpeed(100);
                    break;
            }
        }
        System.out.println(button);
    }

    @Override
    public void onCollisionDetection(int distance) {
        driveSystem.emergencyStop();
        notifications.emergencyNotification();
    }

    //TODO: Add functional note playback
    public void testSong(){
        Buzzer buzzer = new Buzzer(6);
    }

}