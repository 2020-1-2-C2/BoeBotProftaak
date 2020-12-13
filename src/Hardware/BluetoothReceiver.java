package Hardware;

import TI.SerialConnection;
import Utils.BluetoothCallback;
import Utils.Updatable;

public class BluetoothReceiver implements Updatable {
    private SerialConnection serialConnection;
    private BluetoothCallback bluetoothCallback;

    /**
     * Enums for the bluetooth communication.
     */
    public enum Commands {
        FORWARD, REVERSE, LEFT, RIGHT, STOP, DEFAULT;
    }

    /**
     * Constructor for the BluetoothReceiver class.
     * @param bluetoothCallback BluetoothCallBack object.
     */
    public BluetoothReceiver(BluetoothCallback bluetoothCallback) {
        this.serialConnection = new SerialConnection(115200);
        this.bluetoothCallback = bluetoothCallback;
    }

    /**
     * Will listen for input.
     * @return Commands
     */
    public Commands listenForSignal() {
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
                default:
                    return Commands.DEFAULT;
            }
        }
        return Commands.DEFAULT;
    }

    /**
     * Allows for external use of the connection. MIGHT GET REMOVED IF PROVEN UNNECESSARY!
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
