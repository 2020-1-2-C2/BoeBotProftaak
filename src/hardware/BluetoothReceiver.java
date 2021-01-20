package hardware;

import TI.SerialConnection;
import utils.BluetoothCallback;
import utils.Updatable;

/**
 * <code>BluetoothReceiver</code> class, which should only have one instance.
 * The methods are used to process received commands from the PC using the GUI.
 * @see utils.Updatable
 * @see utils.BluetoothCallback
 * @see SerialConnection
 * @author Lars Hoendervangers, Berend de Groot, Martijn de Kam, Meindert Kempe
 */
public class BluetoothReceiver implements Updatable {
    private SerialConnection serialConnection;
    private BluetoothCallback bluetoothCallback;

    /**
     * Enums for the bluetooth communication.
     * This enumerator consists of driving directions, speeds and settings/functions.
     */
    public enum Commands {
        FORWARD, REVERSE, LEFT, RIGHT, STOP, DEFAULT,
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
        START_ROUTE, STOP_ROUTE, AUTO_CALIBRATE, CALIBRATE,
        CONNECT, DISCONNECT
    }

    /**
     * Constructor for the <code>BluetoothReceiver</code> class.
     * @param bluetoothCallback <code>BluetoothCallBack</code> object.
     */
    public BluetoothReceiver(BluetoothCallback bluetoothCallback) {
        this.serialConnection = new SerialConnection(115200);
        this.bluetoothCallback = bluetoothCallback;
    }

    /**
     * Will return the int that is received via bluetooth. Only used to get route coords.
     * @return int this is the integer that is received via bluetooth.
     */
    public int listenForCoords() {
        if (this.serialConnection.available() > 0) {
            return this.serialConnection.readByte();
        } else {
            return 0;
        }
    }

    /**
     * Will listen for input.
     * @return Bluetooth Command
     */
    private Commands listenForSignal() {
        if (this.serialConnection.available() > 0) {
            int data = this.serialConnection.readByte();
            this.serialConnection.writeByte(data);
            System.out.println("Received: " + data);

            switch (data) {
                case 87:
                    //Key: W
                    return Commands.FORWARD;
                case 65:
                    //Key: A
                    return Commands.LEFT;
                case 83:
                    //Key: S
                    return Commands.REVERSE;
                case 68:
                    //Key: D
                    return Commands.RIGHT;
                case 16:
                    //Key: DLE (Data Link Escape, the GUI sends this as an integer rather than a character/string.)
                    return Commands.STOP;
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
                case 32:
                    //Key: Space bar
                    //Start reading route information.
                    return Commands.START_ROUTE;
                case 126:
                    //Key: ~
                    //Stop reading route information.
                    return Commands.STOP_ROUTE;
                case 37:
                    //Key: %
                    return Commands.AUTO_CALIBRATE;
                case 43:
                    //Key: +
                    return Commands.CALIBRATE;
                case 17:
                    //Key: DC1 (Device Control 1, the GUI sends this as an integer rather than a character/string.)
                    return Commands.CONNECT;
                case 24:
                    //Key: CAN (Cancel, the GUI sends this as an integer rather than a character/string.
                    return Commands.DISCONNECT;
                default:
                    return Commands.DEFAULT;
            }
        } else {
            return Commands.DEFAULT;
        }
    }

    /**
     * The <code>Update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * @see Updatable#update()
     */
    @Override
    public void update() {
        this.bluetoothCallback.onBluetoothReceive(listenForSignal());
    }
}
