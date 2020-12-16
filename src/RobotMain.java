import Hardware.*;
import Logic.*;
import TI.BoeBot;
import TI.PinMode;
import Utils.*;

import java.util.ArrayList;
import java.util.Collections;

public class RobotMain implements InfraredCallback, CollisionDetectionCallback, BluetoothCallback {

    private ArrayList<Updatable> updatables = new ArrayList<>();
    private DriveSystem driveSystem;
    private Notifications notifications;
    private boolean running = true;
    private Shapes shapes;

    private Jingle jingle = new Jingle();

    public static void main(String[] args) {
        RobotMain main = new RobotMain();
        main.initialise();
        main.run();
    }

    /**
     * Initialise all relevant objects for the bot
     * Add the necessary objects to the updatables list
     */
    public void initialise() {
        // creating all the different objects which will be used
        InfraredReceiver infraredReceiver = new InfraredReceiver(0, this);
        BluetoothReceiver bluetoothReceiver = new BluetoothReceiver(this);

        CollisionDetection collisionDetection = new CollisionDetection(this);
        UltraSonicReceiver ultraSonicReceiver = new UltraSonicReceiver(1, 2, collisionDetection);

        Motor servoMotor = new ServoMotor(new DirectionalServo(12, 1), new DirectionalServo(13, -1));
        this.driveSystem = new DriveSystem(servoMotor);
        this.shapes = new Shapes(this.driveSystem);
        LineFollower lineFollower = new LineFollower(8, 9, driveSystem);

        Buzzer buzzer = new Buzzer(6);
        ArrayList<Buzzer> buzzers = new ArrayList<>();
        buzzers.add(buzzer);

        NeoPixelLed neoPixelLed0 = new NeoPixelLed(0);
        NeoPixelLed neoPixelLed1 = new NeoPixelLed(1);
        NeoPixelLed neoPixelLed2 = new NeoPixelLed(2);
        NeoPixelLed neoPixelLed3 = new NeoPixelLed(3);
        NeoPixelLed neoPixelLed4 = new NeoPixelLed(4);
        NeoPixelLed neoPixelLed5 = new NeoPixelLed(5);

        //Adds all the NeoPixelLeds to an arraylist.
        ArrayList<NeoPixelLed> neoPixelLeds = new ArrayList<>();
        neoPixelLeds.add(neoPixelLed0);
        neoPixelLeds.add(neoPixelLed1);
        neoPixelLeds.add(neoPixelLed2);
        neoPixelLeds.add(neoPixelLed3);
        neoPixelLeds.add(neoPixelLed4);
        neoPixelLeds.add(neoPixelLed5);

        BoeBot.rgbShow();
        notifications = new Notifications(buzzers, neoPixelLeds);

//        Adds all the updatables to an arraylist.
        Collections.addAll(this.updatables, infraredReceiver, ultraSonicReceiver, collisionDetection,
                this.driveSystem, buzzer, servoMotor, this.shapes, bluetoothReceiver, this.notifications,
                neoPixelLed0, neoPixelLed1, neoPixelLed2, neoPixelLed3, neoPixelLed4, neoPixelLed5);


//        updatables.add(infraredReceiver);
//        updatables.add(ultraSonicReceiver);
//        updatables.add(collisionDetection);
//        updatables.add(driveSystem);
//        updatables.add(buzzer);
//        updatables.add(servoMotor);
//        updatables.add(this.shapes);
//        updatables.add(bluetoothReceiver);
//        updatables.add(notifications);
//
//        for (NeoPixelLed neoPixelLed : neoPixelLeds) {
//            updatables.add(neoPixelLed);
//        }
    }

    /**
     * Main run method for the boebot logic.
     */
    public void run() {
        while (running) {
            for (Updatable u : updatables) {
                u.update();
            }
            BoeBot.wait(1);
        }
    }

    /**
     * Receives the pressed button on the infrared remote and tells the bot to do the action combined with the button
     *
     * @param button received binary code for the button pressed on the infrared remote.
     */
    @Override
    public void onInfraredButton(int button) {
        notifications.remoteNotification();
        switch (button) {
            case InfraredReceiver.POWER:
                driveSystem.stop();
                break;
            case InfraredReceiver.FORWARD:
                driveSystem.setDirection(DriveSystem.FORWARD);
                break;
            case InfraredReceiver.BACKWARD:
                driveSystem.setDirection(DriveSystem.BACKWARD);
                break;
            case InfraredReceiver.RIGHT:
                driveSystem.turnRight();
                break;
            case InfraredReceiver.LEFT:
                driveSystem.turnLeft();
                break;
            case InfraredReceiver.ONE:
                driveSystem.setSpeed(10);
                break;
            case InfraredReceiver.TWO:
                driveSystem.setSpeed(20);
                break;
            case InfraredReceiver.THREE:
                driveSystem.setSpeed(30);
                break;
            case InfraredReceiver.FOUR:
                driveSystem.setSpeed(40);
                break;
            case InfraredReceiver.FIVE:
                driveSystem.setSpeed(50);
                break;
            case InfraredReceiver.SIX:
                driveSystem.setSpeed(60);
                break;
            case InfraredReceiver.SEVEN:
                driveSystem.setSpeed(70);
                break;
            case InfraredReceiver.EIGHT:
                driveSystem.setSpeed(80);
                break;
            case InfraredReceiver.NINE:
                driveSystem.setSpeed(90);
                break;
            case InfraredReceiver.ZERO:
                driveSystem.setSpeed(100);
                break;
            case InfraredReceiver.TRIANGLE:
                this.shapes.beginShape(Shapes.Shape.TRIANGLE);
                break;
            case InfraredReceiver.TVVCR:
                this.shapes.beginShape(Shapes.Shape.CIRCLE);
                break;
        }
        System.out.println(button);
    }

    @Override
    public void onBluetoothReceive(BluetoothReceiver.Commands command) {
        if (!command.equals(BluetoothReceiver.Commands.DEFAULT)) {
            switch (command) {
                case FORWARD:
                    driveSystem.setDirection(1);
                    break;
                case REVERSE:
                    driveSystem.setDirection(-1);
                    break;
                case STOP:
                    driveSystem.stop();
                    break;
                case LEFT:
                    driveSystem.turnLeft();
                    break;
                case RIGHT:
                    driveSystem.turnRight();
                    break;
                case ONE:
                    driveSystem.setSpeed(10);
                    break;
                case TWO:
                    driveSystem.setSpeed(20);
                    break;
                case THREE:
                    driveSystem.setSpeed(30);
                    break;
                case FOUR:
                    driveSystem.setSpeed(40);
                    break;
                case FIVE:
                    driveSystem.setSpeed(50);
                    break;
                case SIX:
                    driveSystem.setSpeed(60);
                    break;
                case SEVEN:
                    driveSystem.setSpeed(70);
                    break;
                case EIGHT:
                    driveSystem.setSpeed(80);
                    break;
                case NINE:
                    driveSystem.setSpeed(90);
                    break;
                case TEN:
                    driveSystem.setSpeed(100);
                    break;
            }
            System.out.println(command);
        }
    }

    /**
     * Receive the distance from the ultrasonic receiver and sets the bot to a certain max allowable speed or an emergency stop according to the distance
     *
     * @param distance distance from collision in cm
     */
    @Override
    public void onCollisionDetection(int distance) {
        if (distance < 20) {
            // Prevent calling emergency stop if the speed is already 0, otherwise turning of the BoeBot is also prevented.
            if (driveSystem.getCurrentSpeed() != 0 && driveSystem.getCurrentMaxSpeed() != 0) {
                driveSystem.setCurrentMaxSpeed(0);
                driveSystem.emergencyStop();
                // TODO Disable emergency notification outside of emergency stop state.
                notifications.emergencyNotification();
                System.out.println("Emergency stop");
            }
        } else if (distance < 30) {
            driveSystem.setCurrentMaxSpeed(10);
        } else if (distance < 40) {
            driveSystem.setCurrentMaxSpeed(20);
        } else if (distance < 50) {
            driveSystem.setCurrentMaxSpeed(30);
        } else if (distance < 60) {
            driveSystem.setCurrentMaxSpeed(40);
        } else if (distance < 70) {
            driveSystem.setCurrentMaxSpeed(50);
        } else if (distance < 80) {
            driveSystem.setCurrentMaxSpeed(60);
        } else if (distance < 90) {
            driveSystem.setCurrentMaxSpeed(70);
        } else if (distance < 100) {
            driveSystem.setCurrentMaxSpeed(80);
        } else if (distance < 110) {
            driveSystem.setCurrentMaxSpeed(90);
        } else {
            driveSystem.setCurrentMaxSpeed(100);
        }

        // if the current speed is greater than the newly set allowable maximum then decrease it to the max allowable speed
        if (driveSystem.getCurrentSpeed() > driveSystem.getCurrentMaxSpeed()) {
            driveSystem.setSpeed(driveSystem.getCurrentMaxSpeed());
        }

    }

    //Plays the first part of the melody of Somebody that I used to know by Gotye
    public void testSong(Buzzer buzzer) {
        System.out.println("Entered Somebody that I used to know by Gotye");
        buzzer.playSong(jingle.somebodyThatIUsedToKnow());
    }

    //TODO: Decide where to update this
    public void update() {
        if (driveSystem.getDirection() == DriveSystem.BACKWARD) {
            notifications.drivingBackwardsNotification();
        }
    }


}