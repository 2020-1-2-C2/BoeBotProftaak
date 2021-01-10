import Hardware.*;
import Logic.*;
import Logic.Notification.*;
import TI.BoeBot;
import Utils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

//General software TODO-list:
//TODO: Put annotations in the documentation in the right order.
//TODO: Add latency compensation for the Buzzer.
//TODO: Fix grammar + spelling in the documentation & comments.
//TODO: Make sure we use UK-English instead of US-English in all classes.
//TODO: Check versions.
//TODO: Make sure all "direction" attributes share common values across the whole program. Consistency makes everything clearer.
//TODO: Remove .java and use <code> </code> and <a href=""> </a> instead.
//TODO: Use "this" instead of "the" when talking about a specific instance on comments.

//Class specific TODO-list:
//TODO: Look at version-control and decide on the software's version.
//TODO: Add backwards functionality for NavigationSystem.java and Route.java.
//TODO: Write documentation for BluetoothCallback.java.
//TODO: Write documentation for InfraredCallback.java.
//TODO: Write documentation for LineFollowCallback.java.
//TODO: Write documentation for UltraSonicCallback.java.
//TODO: Write documentation for ServoMotor.java.
//TODO: Write documentation for UltraSonicReceiver.java.
//TODO: Write documentation for Updatable.java.
//TODO: Write documentation for DriveSystem.java.
//TODO: Improve documentation for Shapes.java.
//TODO: Improve documentation for NavigationSystem.java.

//For Berend
//TODO: Finish Jingles (Make them short and easy to recognize!)
//TODO: Pick a different shade of green, since it looks too much like yellow.
//TODO: Make notifications play automatically.
//TODO: Final grammar check before submitting.

/** Main class of the BoeBot, often described as being the brain of the robot.
 *
 * @author Projectgroep C2 - Berend de Groot, Lars Hoendervangers, Capser Lous, Martijn de Kam, Meindert Kempe, Tom Martens
 * @version 1.0
 */
public class IntelligentFoodAllocationDevice implements CollisionDetectionCallback, BlueToothControllerCallback, InfraredControllerCallback {

    //TODO: Check whether you can use "Whom" when talking about code.
    /**
     * ArrayList containing all the hardware components' instances whom all implement the <a href="{@docRoot}/Util/Updatable.html">Updatable.java</a> interface.
     * The <code>update()</code> method in that interface is being run constantly in the <code>run()</code> method in this class.
     * @see #run()
     * @see Updatable#update()
     */

    private ArrayList<Updatable> updatables = new ArrayList<>();
    private DriveSystem driveSystem;
    private NotificationSystemController notificationSystemController;
    private BluetoothController bluetoothController;

    private HashMap<Integer, Executable> onInfraredCommandMap;
    private HashMap<BluetoothReceiver.Commands, Executable> onBlueToothCommandMap;


    /**
     * Creates an instance of itself, and then initializes the attributes. After this has happened it goes through all the updatables in an endless loop. <p>
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
        this.notificationSystemController = new NotificationSystemController();
        this.bluetoothController = new BluetoothController(this);

        InfraredController infraredController = new InfraredController(this);
        CollisionDetection collisionDetection = new CollisionDetection(this);
        LineFollowerController lineFollowerController = new LineFollowerController(driveSystem);

        //Adds all the updatables to an ArrayList.
        Collections.addAll(this.updatables, collisionDetection, this.driveSystem,
                shapes, infraredController, this.notificationSystemController,
                lineFollowerController, bluetoothController
        );

        // initialises the HashMaps which holds the commands to use.
        this.onInfraredCommandMap = new HashMap<>();
        this.onBlueToothCommandMap = new HashMap<>();
        // Fills the HashMaps with commands.
        // Infrared button commands:
        this.onInfraredCommandMap.put(InfraredReceiver.POWER, () -> {
            this.driveSystem.stop();
        });
        this.onInfraredCommandMap.put(InfraredReceiver.FORWARD, () -> {
            this.driveSystem.setDirection(DriveSystem.FORWARD);
        });
        this.onInfraredCommandMap.put(InfraredReceiver.BACKWARD, () -> {
            this.driveSystem.setDirection(DriveSystem.BACKWARD);
        });
        this.onInfraredCommandMap.put(InfraredReceiver.RIGHT, () -> {
            this.driveSystem.turnRight();
        });
        this.onInfraredCommandMap.put(InfraredReceiver.LEFT, () -> {
            this.driveSystem.turnLeft();
        });
        this.onInfraredCommandMap.put(InfraredReceiver.ONE, () -> {
            this.driveSystem.setSpeed(10);
            setNotification(new DisconnectedNotification(this.notificationSystemController.getBuzzer(), this.notificationSystemController.getNeoPixelLeds()));
        });
        this.onInfraredCommandMap.put(InfraredReceiver.TWO, () -> {
            this.driveSystem.setSpeed(20);
            setNotification(new ConnectedNotification(this.notificationSystemController.getBuzzer(), this.notificationSystemController.getNeoPixelLeds()));
        });
        this.onInfraredCommandMap.put(InfraredReceiver.THREE, () -> {
            this.driveSystem.setSpeed(30);
            setNotification(new EmptyNotification(this.notificationSystemController.getBuzzer(), this.notificationSystemController.getNeoPixelLeds()));
        });
        this.onInfraredCommandMap.put(InfraredReceiver.FOUR, () -> {
            this.driveSystem.setSpeed(40);
        });
        this.onInfraredCommandMap.put(InfraredReceiver.FIVE, () -> {
            this.driveSystem.setSpeed(50);
        });
        this.onInfraredCommandMap.put(InfraredReceiver.SIX, () -> {
            this.driveSystem.setSpeed(60);
        });
        this.onInfraredCommandMap.put(InfraredReceiver.SEVEN, () -> {
            this.driveSystem.setSpeed(70);
        });
        this.onInfraredCommandMap.put(InfraredReceiver.EIGHT, () -> {
            this.driveSystem.setSpeed(80);
        });
        this.onInfraredCommandMap.put(InfraredReceiver.NINE, () -> {
            this.driveSystem.setSpeed(90);
            //TODO: Test-code, should be removed once tested.
            this.driveSystem.followRoute(new NavigationSystem(0, 0, 3, 3).getRoute());
        });
        this.onInfraredCommandMap.put(InfraredReceiver.ZERO, () -> {
            this.driveSystem.setSpeed(100);
        });
        this.onInfraredCommandMap.put(InfraredReceiver.TRIANGLE, () -> {
            this.driveSystem.stop();
        });
        this.onInfraredCommandMap.put(InfraredReceiver.TVVCR, () -> {
            this.driveSystem.stop();
        });

        // BluetoothReceiver commands.
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.FORWARD, () -> {
           this.driveSystem.setDirection(1);
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.REVERSE, () -> {
            this.driveSystem.setDirection(-1);
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.STOP, () -> {
            this.driveSystem.stop();
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.LEFT, () -> {
            this.driveSystem.turnLeft();
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.RIGHT, () -> {
            this.driveSystem.turnRight();
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.ONE, () -> {
            this.driveSystem.setSpeed(10);
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.TWO, () -> {
            this.driveSystem.setSpeed(20);
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.THREE, () -> {
            this.driveSystem.setSpeed(30);
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.FOUR, () -> {
            this.driveSystem.setSpeed(40);
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.FIVE, () -> {
            this.driveSystem.setSpeed(50);
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.SIX, () -> {
            this.driveSystem.setSpeed(60);
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.SEVEN, () -> {
            this.driveSystem.setSpeed(70);
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.EIGHT, () -> {
            this.driveSystem.setSpeed(80);
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.NINE, () -> {
            this.driveSystem.setSpeed(90);
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.TEN, () -> {
            this.driveSystem.setSpeed(100);
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.START_ROUTE, () -> {
            boolean reading = true;
            String route = "";
            //TODO this is a big blocking call with the loop, is it possible to implement this in a different way using Updatable?
            //This is a block and call, but should only be used when the bot is stationary.
            while (reading) {
                int data = this.bluetoothController.getBluetoothReceiver().listenForCoords();
                //Stop signal.
                if (data == 126) {
                    reading = false;
                    //A length of 2 means that only the end point is received.
                    if (route.length() == 2) {
                        NavigationSystem navigationSystem = new NavigationSystem(route.charAt(0), route.charAt(1));
                        navigationSystem.getRoute();
                        //A length of 4 means that there is a start and end point.
                    } else if (route.length() == 4) {
                        NavigationSystem navigationSystem = new NavigationSystem(route.charAt(0), route.charAt(1), route.charAt(2), route.charAt(3));
                        navigationSystem.getRoute();
                    } else {
                        System.out.println("Invalid route data received.");
                    }
                    System.out.println(route);
                    route = "";
                } else {
                    route += ((char) data);
                    //TODO: Check whether we should use StringBuilder for better performance. ; String concatenation.
                }
            }
        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.STOP_ROUTE, () -> {

        });
        this.onBlueToothCommandMap.put(BluetoothReceiver.Commands.DEFAULT, () -> {
            // This shouldn't do anything.
            //TODO: Check if this can be removed, and if so, remove it.
        });
    }

    /**
     * Main run method for the BoeBot logic.
     * {@link #updatables}
     */
    private void run() {
        while (true) { //This used to be this.isRunning, but that always returned true.
            for (Updatable u : this.updatables) {
                u.update();
            }
            BoeBot.wait(1);
        }
    }

    /**
     * Receive the distance from the ultrasonic receiver and sets the bot to a certain max allowable speed or an emergency stop according to the distance.
     * @param distance Distance from collision in cm.
     */
    @Override
    public void onCollisionDetection(int distance) {
        if (distance < 20) {
            // Prevent calling emergency stop if the speed is already 0, otherwise turning of the BoeBot is also prevented.
            if (this.driveSystem.getCurrentSpeed() != 0 && this.driveSystem.getCurrentMaxSpeed() != 0) {
                this.driveSystem.followLine(false);
                this.driveSystem.stopFollowingRoute();
                this.driveSystem.setCurrentMaxSpeed(0);
                this.driveSystem.emergencyStop();
                //TODO: Consider putting this in DriveSystem.java?
                setNotification(new EmergencyStopNotification(this.notificationSystemController.getBuzzer(), this.notificationSystemController.getNeoPixelLeds()));
                System.out.println("Emergency stop");
            }
        } else if (distance < 30) {
            this.driveSystem.setCurrentMaxSpeed(10);
        } else if (distance < 40) {
            this.driveSystem.setCurrentMaxSpeed(20);
        } else if (distance < 50) {
            this.driveSystem.setCurrentMaxSpeed(30);
        } else if (distance < 60) {
            this.driveSystem.setCurrentMaxSpeed(40);
        } else if (distance < 70) {
            this.driveSystem.setCurrentMaxSpeed(50);
        } else if (distance < 80) {
            this.driveSystem.setCurrentMaxSpeed(60);
        } else if (distance < 90) {
            this.driveSystem.setCurrentMaxSpeed(70);
        } else if (distance < 100) {
            this.driveSystem.setCurrentMaxSpeed(80);
        } else if (distance < 110) {
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
     * Used to execute <code>notificationsSpecificMethod()</code> for the notification given in its parameter. <p>
     * This method also sets <a href="{@docRoot}/Hardware/NeoPixelLed.html">NeoPixelLeds' setShouldBeOn()</a> to <code>true</code>.
     * @param notification Notification that should have its <code>notificationSpecificMethod()</code> executed.
     * @see AbstractNotification#notificationSpecificMethod()
     * @see NeoPixelLed#setShouldBeOn(boolean)
     */
    public void setNotification(AbstractNotification notification) {
        //TODO why is this inside the main and not somewhere in logic?
        for (NeoPixelLed neoPixelLed : this.notificationSystemController.getNeoPixelLeds()) {
            neoPixelLed.setShouldBeOn(true);
        }
        notification.notificationSpecificMethod();
    }

    /**
     * Receives a valid button press from the infraredController and executes the command associated with it.
     * @param button Int representing a button the remote.
     */
    @Override
    public void onInfraredControllerCommand(int button) {
        setNotification(new RemoteNotification(this.notificationSystemController.getBuzzer(), this.notificationSystemController.getNeoPixelLeds()));
        setNotification(new EmptyNotification(this.notificationSystemController.getBuzzer(), this.notificationSystemController.getNeoPixelLeds()));

        this.onInfraredCommandMap.get(button).Execute();

        // If the received command is following a route, then the program should stop following the route and stop following lines.
        if (this.driveSystem.isFollowingRoute()) {
            this.driveSystem.followLine(false);
            this.driveSystem.stopFollowingRoute();
        }
    }

    /**
     * This method will listen for commands that are sent by bluetooth. If the START_ROUTE command is called it will start a while loop for how ever long it will take to
     * read the data. This will usually only take a couple ms. Then it will put the number in a string. That string will then be split into separate integers that are
     * used to create a new NavigationSystem object. (First int = x, second int = y)
     * TODO: Possible string length check, depends on if we want to use more coords.
     * @param command //TODO: SPECIFY THIS!
     */
    @Override
    public void onBlueToothControllerCommand(BluetoothReceiver.Commands command) {
       this.onBlueToothCommandMap.get(command).Execute();
    }
}
