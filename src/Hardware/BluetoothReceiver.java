package Hardware;

import Logic.Route;
import TI.SerialConnection;
import Utils.BluetoothCallback;
import Utils.Updatable;

import java.util.ArrayList;

public class BluetoothReceiver implements Updatable {
    private SerialConnection serialConnection;
    private BluetoothCallback bluetoothCallback;
    private ArrayList<Integer> routeDirections;
    private Route route;

    /**
     * Enums for the bluetooth communication.
     */
    public enum Commands {
        FORWARD, REVERSE, LEFT, RIGHT, STOP, DEFAULT,
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
        START_ROUTE
    }

    /**
     * Constructor for the BluetoothReceiver class.
     *
     * @param bluetoothCallback BluetoothCallBack object.
     */
    public BluetoothReceiver(BluetoothCallback bluetoothCallback) {
        this.serialConnection = new SerialConnection(115200);
        this.bluetoothCallback = bluetoothCallback;
        this.routeDirections = new ArrayList<>();
        this.route = new Route(this.routeDirections);
    }

    /**
     * Will listen for input.
     *
     * @return Commands
     */
    private Commands listenForSignal() {
        if (this.serialConnection.available() > 0) {
            int data = this.serialConnection.readByte();
            this.serialConnection.writeByte(data);
            System.out.println("Received: " + data);
            boolean read = true;
            switch (data) {
                //Directions
                case 87:
                    return Commands.FORWARD;
                case 65:
                    return Commands.LEFT;
                case 83:
                    return Commands.REVERSE;
                case 68:
                    return Commands.RIGHT;
                case 16:
                    return Commands.STOP;
                //Speeds
                case 1:
                    return Commands.ONE;
                case 2:
                    return Commands.TWO;
                case 3:
                    return Commands.THREE;
                case 4:
                    return Commands.FOUR;
                case 5:
                    return Commands.FIVE;
                case 6:
                    return Commands.SIX;
                case 7:
                    return Commands.SEVEN;
                case 8:
                    return Commands.EIGHT;
                case 9:
                    return Commands.NINE;
                case 10:
                    return Commands.TEN;
                //Start route command TODO: Make it functional, can be done when the algorithm is implemented.
                case 32:
                    return Commands.START_ROUTE;
                default:
                    return Commands.DEFAULT;
            }
        }
        return Commands.DEFAULT;
    }

    /**
     * Allows for external use of the connection. MIGHT GET REMOVED IF PROVEN UNNECESSARY!
     *
     * @return Serialconnection object
     */
    public SerialConnection getSerialConnection() {
        return serialConnection;
    }

    /**
     * Update method.
     */
    @Override
    public void update() {
        this.bluetoothCallback.onBluetoothReceive(listenForSignal());
    }

}
