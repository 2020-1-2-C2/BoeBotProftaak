import Hardware.*;
import Logic.*;
import TI.BoeBot;
import TI.PinMode;
import Utils.*;

import java.util.ArrayList;

public class RobotMain implements InfraredCallback, CollisionDetectionCallback, BluetoothCallback {

    private ArrayList<Updatable> updatables = new ArrayList<>();
    private DriveSystem driveSystem;
    private Notifications notifications;
    private boolean running = true;
    private Shapes shapes;

    private Jingle jingle = new Jingle();

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
        BluetoothReceiver bluetoothReceiver = new BluetoothReceiver(this);

        CollisionDetection collisionDetection = new CollisionDetection(this);
        UltraSonicReceiver ultraSonicReceiver = new UltraSonicReceiver(1, 2, collisionDetection);

        Motor servoMotor = new ServoMotor(new DirectionalServo(12, 1), new DirectionalServo(13, -1));
        driveSystem = new DriveSystem(servoMotor);
        this.shapes = new Shapes(this.driveSystem);

        Buzzer buzzer = new Buzzer(6);
        BoeBot.setMode(6, PinMode.Output);
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
        notifications = new Notifications(buzzers, neoPixelLeds);

//        Adds all the updatables to an arraylist.
        updatables.add(infraredReceiver);
        updatables.add(ultraSonicReceiver);
        updatables.add(collisionDetection);
        updatables.add(driveSystem);
        updatables.add(buzzer);
        updatables.add(servoMotor);
        updatables.add(this.shapes);
        updatables.add(bluetoothReceiver);
        for (NeoPixelLed neoPixelLed : neoPixelLeds){
            updatables.add(neoPixelLed);
        }

        testSong(buzzer);

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
                case "triangle":
                    this.shapes.beginShape(Shapes.Shape.TRIANGLE);
                    break;
                case "tvvcr":
                    this.shapes.beginShape(Shapes.Shape.CIRCLE);
                    break;
            }
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

    @Override
    public void onCollisionDetection(int distance) {
        driveSystem.emergencyStop();
        notifications.emergencyNotification();
    }

    //Plays the first part of the melody of Somebody that I used to know by Gotye
    public void testSong(Buzzer buzzer){
        System.out.println("Entered Somebody that I used to know by Gotye");
        buzzer.playSong(jingle.somebodyThatIUsedToKnow());
    }

    //TODO: Decide where to update this
    public void update(){
        if (driveSystem.getDirection() == -1){
            notifications.drivingBackwardsNotification();
        }
    }


}