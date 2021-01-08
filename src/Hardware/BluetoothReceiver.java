package Hardware;

import TI.SerialConnection;
import Utils.BluetoothCallback;
import Utils.Updatable;

import java.util.ArrayList;

/**
 * <code>BluetoothReceiver</code> class, which should only have one instance. The methods are used to process received commands from the PC using the GUI.
 * @see Utils.Updatable
 * @see Utils.BluetoothCallback
 * @see SerialConnection
 */
public class BluetoothReceiver implements Updatable {
    private SerialConnection serialConnection;
    private BluetoothCallback bluetoothCallback;

    /**
     * Enums for the bluetooth communication.
     * This enumerator consists of FORWARD, REVERSE, LEFT, RIGHT, STOP, DEFAULT and the numbers 1 - 10.
     */
    public enum Commands {
        FORWARD, REVERSE, LEFT, RIGHT, STOP, DEFAULT,
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
        START_ROUTE, STOP_ROUTE
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
     * @return int
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
     * @return Commands
     */
    private Commands listenForSignal() {
        if (this.serialConnection.available() > 0) {
            int data = this.serialConnection.readByte();
            this.serialConnection.writeByte(data);
            System.out.println("Received: " + data);

            switch (data) {
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
                    //Start reading route information
                    return Commands.START_ROUTE;
                case 126:
                    //Stop reading route information
                    return Commands.STOP_ROUTE;
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
