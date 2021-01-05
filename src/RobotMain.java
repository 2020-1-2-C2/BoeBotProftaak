import Hardware.*;
import Logic.*;
import Logic.Notification.*;
import TI.BoeBot;
import Utils.*;

import java.util.ArrayList;
import java.util.Collections;

//General software TODO-list:
//TODO: Put annotations in the documentation in the right order.
//TODO: Add latency compensation for the Buzzer.
//TODO: Look at usage of @author.
//TODO: Fix grammar + spelling in the documentation & comments.
//TODO: Make sure we use UK-English instead of US-English in all classes.
//TODO: Remove SOUTs.
//TODO: Check versions.
//TODO: Change @see to specific methods (or constructors) if appropriate.

//Class specific TODO-list:
//TODO: Look at version-control and decide on the software's version.
//TODO: Remove DefaultLed, since we do not use it anymore.
//TODO: Remove RGBLed, since we do not use it anymore.
//TODO: Delete comments in LineFollower.java
//TODO: Change some text in LineFollower.java so that everything is in English.
//TODO: Write documentation for BluetoothCallback.java
//TODO: Write documentation for InfraredCallback.java
//TODO: Write documentation for LineFollowCallback.java
//TODO: Write documentation for UltraSonicCallback.java
//TODO: Write documentation for ServoMotor.java
//TODO: Write documentation for UltraSonicReceiver.java
//TODO: Write documentation for Updatable.java
//TODO: Improve documentation for Shapes.java

//For Berend
//TODO: Make RemoteNotification.java
//TODO: Finish Jingles (Make them short and easy to recognize!)
//TODO: Pick a different shade of green, since it looks too much like yellow.
//TODO: Make notifications play automatically.

/** Main class of the BoeBot, often described as being the brain of the robot.
 *
 * @author Projectgroep C2 - Berend de Groot, Lars Hoendervangers, Capser Lous, Martijn de Kam, Meindert Kempe, Tom Martens
 * @version 1.0
 */
public class RobotMain implements InfraredCallback, CollisionDetectionCallback, BluetoothCallback {

    private ArrayList<Updatable> updatables = new ArrayList<>();
    private DriveSystem driveSystem;
    private boolean running = true;
    private Shapes shapes;

    private Jingle jingle = new Jingle();


    private Buzzer buzzer = new Buzzer(3);
    private ArrayList<Buzzer> buzzers;

    private NeoPixelLed neoPixelLed0 = new NeoPixelLed(0);
    private NeoPixelLed neoPixelLed1 = new NeoPixelLed(1);
    private NeoPixelLed neoPixelLed2 = new NeoPixelLed(2);
    private NeoPixelLed neoPixelLed3 = new NeoPixelLed(3);
    private NeoPixelLed neoPixelLed4 = new NeoPixelLed(4);
    private NeoPixelLed neoPixelLed5 = new NeoPixelLed(5);
    private ArrayList<NeoPixelLed> neoPixelLeds;

    //TODO: Write documentation for this (or not?)
    /**
     * Do this ASAP
     * @param args Do this ASAP
     */
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

        //TODO: Don't hardcode orientation like this.
        Motor servoMotor = new ServoMotor(new DirectionalServo(12, 1), new DirectionalServo(13, -1));
        this.driveSystem = new DriveSystem(servoMotor);
        this.shapes = new Shapes(this.driveSystem);
        //TODO: Uncomment lineFollower initialization.
        //LineFollower lineFollower = new LineFollower(2, 1, driveSystem);

        this.buzzers = new ArrayList<>();
        this.buzzers.add(buzzer);

        //Adds all the NeoPixelLeds to an arraylist.
        this.neoPixelLeds = new ArrayList<>();
        neoPixelLeds.add(neoPixelLed0);
        neoPixelLeds.add(neoPixelLed1);
        neoPixelLeds.add(neoPixelLed2);
        neoPixelLeds.add(neoPixelLed3);
        neoPixelLeds.add(neoPixelLed4);
        neoPixelLeds.add(neoPixelLed5);

        //Adds all the updatables to an arraylist.
        //TODO: Comment out lineFollower.
        //TODO: Add the neoPixelLeds arraylist instead of all the neoPixelLeds individually.
        Collections.addAll(this.updatables, infraredReceiver, ultraSonicReceiver, collisionDetection,
                this.driveSystem, this.buzzer, servoMotor, this.shapes, bluetoothReceiver, /*lineFollower*/
                neoPixelLed0, neoPixelLed1, neoPixelLed2, neoPixelLed3, neoPixelLed4, neoPixelLed5
        );
    }

    /**
     * Main run method for the BoeBot logic.
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
        setNotification(new RemoteNotification(this.buzzers, this.neoPixelLeds));
        boolean recognized = true;
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
                setNotification(new DisconnectedNotification(this.buzzers, this.neoPixelLeds));
                break;
            case InfraredReceiver.TWO:
                driveSystem.setSpeed(20);
                setNotification(new ConnectedNotification(this.buzzers, this.neoPixelLeds));
                break;
            case InfraredReceiver.THREE:
                driveSystem.setSpeed(30);
                setNotification(new EmptyNotification(this.buzzers, this.neoPixelLeds));
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
            default:
                recognized = false;
                break;
        }
        if (recognized) {
            driveSystem.followLine(false);
            driveSystem.stopFollowingRoute();
        }
    }

    //TODO Input for route following needs to be interpreted and converted into a route object, after which the DriveSystem.followRoute(route) method can be called to start following that route
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
                driveSystem.followLine(false);
                driveSystem.stopFollowingRoute();
                driveSystem.setCurrentMaxSpeed(0);
                driveSystem.emergencyStop();
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

        // If the current speed is greater than the newly set allowable maximum then decrease it to the max allowable speed.
        if (driveSystem.getCurrentSpeed() > driveSystem.getCurrentMaxSpeed()) {
            driveSystem.setSpeed(driveSystem.getCurrentMaxSpeed());
        }
    }

    public void setNotification(AbstractNotification notification){
        for (NeoPixelLed neoPixelLed : this.neoPixelLeds){
            neoPixelLed.setShouldBeOn(true);
        }
        notification.notificationSpecificMethod();
    }



    //Plays the first part of the melody of Somebody that I used to know by Gotye
    //TODO: Remove this after the last test for both the audio & the notifications system.
    public void testSong(Buzzer buzzer) {
        System.out.println("Entered Somebody that I used to know by Gotye");
        buzzer.playSong(jingle.somebodyThatIUsedToKnow());
    }
}