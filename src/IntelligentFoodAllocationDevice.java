import hardware.*;
import logic.*;
import logic.notification.*;
import TI.BoeBot;
import utils.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

//General software TODO-list:
//TODO: Add latency compensation for the Buzzer.

//For Berend
//TODO: Final grammar check before submitting.

/** Main class of the BoeBot, often described as being the brain of the robot.
 * @author Projectgroep C2 - Berend de Groot, Lars Hoendervangers, Capser Lous, Martijn de Kam, Meindert Kempe, Tom Martens
 * @version 1.0
 */
public class IntelligentFoodAllocationDevice implements CollisionDetectionCallback, BlueToothControllerCallback, InfraredControllerCallback {

    private ArrayList<Updatable> updatables = new ArrayList<>();
    private DriveSystem driveSystem;
    private NotificationSystemController notificationSystemController;
    private BluetoothController bluetoothController;
    private LineFollowerController lineFollowerController;

    //Hashmaps containing commands for Infrared-handling and Bluetooth-handling.
    private HashMap<Integer, Executable> onInfraredCommandMap;
    private HashMap<BluetoothReceiver.Commands, Executable> onBlueToothCommandMap;


    /**
     * Creates an instance of itself, and then initializes the attributes.
     * After this has happened it goes through all the updatables in an endless loop. <p>
     * This method is static, and is the default-generated <code>"main"</code> function.
     * @param args Default parameter.
     * @see #initialise()
     * @see #run()
     */
    public static void main(String[] args) {
        IntelligentFoodAllocationDevice main = new IntelligentFoodAllocationDevice();
        main.initialise();
        main.run();
    }

    /**
     * Initialises all relevant objects for the bot.
     * Adds the necessary objects to the <code>updatables</code> list.
     * @see #updatables
     */
    private void initialise() {
        // Creating all the different objects which will be used.
        this.driveSystem = new DriveSystem();
        Shapes shapes = new Shapes(this.driveSystem);
        this.notificationSystemController = new NotificationSystemController(this.driveSystem);
        this.bluetoothController = new BluetoothController(this);
        this.lineFollowerController = new LineFollowerController(this.driveSystem);

        InfraredController infraredController = new InfraredController(this);
        CollisionDetection collisionDetection = new CollisionDetection(this);

        //Adds all the updatables to an ArrayList.
        Collections.addAll(this.updatables, collisionDetection, this.driveSystem,
                shapes, infraredController, this.notificationSystemController,
                this.lineFollowerController, this.bluetoothController
        );

        // Initialises the HashMaps which holds the commands to use.
        this.onInfraredCommandMap = new HashMap<>();
        this.onBlueToothCommandMap = new HashMap<>();
        // Fills the HashMaps with commands.
        // Infrared button commands:
        this.onInfraredCommandMap.put(InfraredReceiver.POWER, () -> this.driveSystem.stop());
        this.onInfraredCommandMap.put(InfraredReceiver.FORWARD, () -> this.driveSystem.setDirection(DriveSystem.FORWARD));
        this.onInfraredCommandMap.put(InfraredReceiver.BACKWARD, () -> this.driveSystem.setDirection(DriveSystem.BACKWARD));
        this.onInfraredCommandMap.put(InfraredReceiver.RIGHT, () -> this.driveSystem.turnRight());
        this.onInfraredCommandMap.put(InfraredReceiver.LEFT, () -> this.driveSystem.turnLeft());
        this.onInfraredCommandMap.put(InfraredReceiver.ONE, () -> this.driveSystem.setSpeed(10));
        this.onInfraredCommandMap.put(InfraredReceiver.TWO, () -> this.driveSystem.setSpeed(20));
        this.onInfraredCommandMap.put(InfraredReceiver.THREE, () -> this.driveSystem.setSpeed(30));
        this.onInfraredCommandMap.put(InfraredReceiver.FOUR, () -> this.driveSystem.setSpeed(40));
        this.onInfraredCommandMap.put(InfraredReceiver.FIVE, () -> this.driveSystem.setSpeed(50));
        this.onInfraredCommandMap.put(InfraredReceiver.SIX, () -> this.driveSystem.setSpeed(60));
        this.onInfraredCommandMap.put(InfraredReceiver.SEVEN, () -> this.driveSystem.setSpeed(70));
        this.onInfraredCommandMap.put(InfraredReceiver.EIGHT, () -> this.driveSystem.setSpeed(80));
        this.onInfraredCommandMap.put(InfraredReceiver.NINE, () -> this.driveSystem.setSpeed(90));
        this.onInfraredCommandMap.put(InfraredReceiver.ZERO, () -> this.driveSystem.setSpeed(100));

        this.onInfraredCommandMap.put(InfraredReceiver.TRIANGLE, () -> shapes.beginShape(Shapes.Shape.TRIANGLE));
        this.onInfraredCommandMap.put(InfraredReceiver.TVVCR, () -> shapes.beginShape(Shapes.Shape.CIRCLE));

        // BluetoothReceiver commands.
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.FORWARD, () -> this.driveSystem.setDirection(DriveSystem.FORWARD));
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.REVERSE, () -> this.driveSystem.setDirection(DriveSystem.BACKWARD));
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.STOP, () -> this.driveSystem.stop());
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.LEFT, () -> this.driveSystem.turnLeft());
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.RIGHT, () -> this.driveSystem.turnRight());
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.ONE, () -> this.driveSystem.setSpeed(10));
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.TWO, () -> this.driveSystem.setSpeed(20));
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.THREE, () -> this.driveSystem.setSpeed(30));
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.FOUR, () -> this.driveSystem.setSpeed(40));
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.FIVE, () -> this.driveSystem.setSpeed(50));
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.SIX, () -> this.driveSystem.setSpeed(60));
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.SEVEN, () -> this.driveSystem.setSpeed(70));
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.EIGHT, () -> this.driveSystem.setSpeed(80));
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.NINE, () -> this.driveSystem.setSpeed(90));
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.TEN, () -> this.driveSystem.setSpeed(100));
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.START_ROUTE, () -> {
            this.notificationSystemController.setNotification(
                    new FollowingRouteNotification(
                            this.notificationSystemController.getBuzzer(), this.notificationSystemController.getNeoPixelLeds()
                    )
            );
            boolean reading = true;
            String route = ""; //Contains raw coords, so "1,1" + "2,2" becomes 1122.
            while (reading) {
                int data = this.bluetoothController.getBluetoothReceiver().listenForCoords();
                if (data == 126) {
                    reading = false;
                } else { route += (char) data; }
            }

            if (route.length() < 3) {
                System.out.println("Route: (2) " + route);
                NavigationSystem navigationSystem = new NavigationSystem(Character.getNumericValue(route.charAt(0)), Character.getNumericValue(route.charAt(1)));
                this.driveSystem.followRoute(navigationSystem.getRoute());
            } else if (route.length() > 2 && route.length() < 5) {
                System.out.println("Route: (4) " + route);
                NavigationSystem navigationSystem = new NavigationSystem(Character.getNumericValue(route.charAt(0)), Character.getNumericValue(route.charAt(1)), Character.getNumericValue(route.charAt(2)), Character.getNumericValue(route.charAt(3)));
                this.driveSystem.followRoute(navigationSystem.getRoute());
            } else if (route.length() > 4) {
                System.out.println("Route: (5+) " + route);
                NavigationSystem navigationSystem = new NavigationSystem(0, 0);
                navigationSystem.getCompleteRoute().clear();
                for (int i = 0; i < route.length(); i++) {
                    navigationSystem.getCompleteRoute().add(route.charAt(i)); //TODO: What is this? This is not how you're supposed to make routes.
                }
                System.out.println("getCompleteRoute: " + navigationSystem.getCompleteRoute()); //Because for some reason we use this.
                this.driveSystem.followRoute(navigationSystem.getRoute());
                System.out.println("HERE WE GO");
                navigationSystem.printRoute();
            }
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.AUTO_CALIBRATE, () -> {
            this.lineFollowerController.calibrate();
        });

        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.CONNECT, () -> this.notificationSystemController.setNotification(
                new ConnectedNotification(this.notificationSystemController.getBuzzer(), this.notificationSystemController.getNeoPixelLeds())));

        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.DISCONNECT, () -> this.notificationSystemController.setNotification(
                new DisconnectedNotification(this.notificationSystemController.getBuzzer(), this.notificationSystemController.getNeoPixelLeds())
        ));

        //NOTE: THE DEFAULT COMMAND IS REQUIRED TO PREVENT THE MAIN WHILE LOOP FROM THROWING A NULL POINTER EXCEPTION.
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.DEFAULT, () -> {
            //Do nothing.
        });
    }

    /**
     * Main run method for the BoeBot logic.
     * {@link #updatables}
     */
    private void run() {
        //This used to be this.isRunning, but that always returned true so it has been replaced with "true".
        while (true) {
            for (Updatable u : this.updatables) {
                u.update();
            }
            BoeBot.wait(1);
        }
    }

    /**
     * Receive the distance from the ultrasonic receiver
     * and sets the bot to a certain max allowable speed or an emergency stop according to the distance.
     * @param distance Distance from collision in cm.
     */
    @Override
    public void onCollisionDetection(int distance) {
        if (distance < 30) {
            // Prevent calling emergency stop if the speed is already 0, otherwise turning of the BoeBot is also prevented.
            if (this.driveSystem.getCurrentSpeed() != 0 && this.driveSystem.getCurrentMaxSpeed() != 0) {
                this.driveSystem.followLine(false);
                this.driveSystem.stopFollowingRoute();
                this.driveSystem.setCurrentMaxSpeed(0);
                this.driveSystem.emergencyStop();
                this.notificationSystemController.setNotification(
                        new EmergencyStopNotification(
                                this.notificationSystemController.getBuzzer(), this.notificationSystemController.getNeoPixelLeds()
                        )
                );
                System.out.println("Emergency stop");
            }
        } else if (distance < 40) {
            this.driveSystem.setCurrentMaxSpeed(10);
        } else if (distance < 50) {
            this.driveSystem.setCurrentMaxSpeed(20);
        } else if (distance < 60) {
            this.driveSystem.setCurrentMaxSpeed(30);
        } else if (distance < 70) {
            this.driveSystem.setCurrentMaxSpeed(40);
        } else if (distance < 80) {
            this.driveSystem.setCurrentMaxSpeed(50);
        } else if (distance < 90) {
            this.driveSystem.setCurrentMaxSpeed(60);
        } else if (distance < 100) {
            this.driveSystem.setCurrentMaxSpeed(70);
        } else if (distance < 110) {
            this.driveSystem.setCurrentMaxSpeed(80);
        } else if (distance < 120) {
            this.driveSystem.setCurrentMaxSpeed(90);
        } else {
            this.driveSystem.setCurrentMaxSpeed(100);
        }

        // If the current speed is greater than the newly set allowable maximum then decrease it to the max allowable speed.
        if (this.driveSystem.getCurrentSpeed() > this.driveSystem.getCurrentMaxSpeed()) {
            this.driveSystem.setSpeed(this.driveSystem.getCurrentMaxSpeed());
        }
    }



    /**
     * Receives a valid button press from the infraredController and executes the command associated with it.
     * @param button Int representing a button the remote.
     */
    @Override
    public void onInfraredControllerCommand(int button) {
        this.notificationSystemController.setNotification(
                new RemoteNotification(this.notificationSystemController.getBuzzer(), this.notificationSystemController.getNeoPixelLeds())
        );
//        this.notificationSystemController.setNotification(
//                new EmptyNotification(this.notificationSystemController.getBuzzer(), this.notificationSystemController.getNeoPixelLeds())
//        );

        this.onInfraredCommandMap.get(button).Execute();

        // If the received command is following a route, then the program should stop following the route and stop following lines.
        if (this.driveSystem.isFollowingRoute()) {
            this.driveSystem.followLine(false);
            this.driveSystem.stopFollowingRoute();
        }
    }

    /**
     * This method will listen for commands that are sent by bluetooth.
     * If the START_ROUTE command is called it will start a while loop for how ever long it will take to
     * read the data. This will usually only take a couple ms. Then it will put the number in a string.
     * That string will then be split into separate integers that are
     * used to create a new NavigationSystem object. (First int = x, second int = y)
     * @param command BluetoothReceiver.Commands
     */
    @Override
    public void onBlueToothControllerCommand(BluetoothReceiver.Commands command) {
       this.onBlueToothCommandMap.get(command).Execute();
    }
}
