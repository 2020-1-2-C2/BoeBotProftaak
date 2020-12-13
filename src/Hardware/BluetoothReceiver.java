package Hardware;

import TI.SerialConnection;
import Utils.BluetoothCallback;
import Utils.Updatable;

public class BluetoothReceiver implements Updatable {
    private SerialConnection serialConnection;
    private BluetoothCallback bluetoothCallback;

    public enum Commands {
        FORWARD, REVERSE, LEFT, RIGHT, STOP, DEFAULT;

/*        public int getCommandValue() {
            switch(this) {
                case FORWARD:
                    return 1;
                case REVERSE:
                    return 2;
                case LEFT:
                    return 3;
                case RIGHT:
                    return 4;
                case STOP:
                    return 5;
                case DEFAULT:
                    return -1;
                default:
                    return -1;
            }
        }*/
    }

    public BluetoothReceiver(BluetoothCallback bluetoothCallback) {
        this.serialConnection = new SerialConnection(115200);
        this.bluetoothCallback = bluetoothCallback;
    }

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

    public SerialConnection getSerialConnection() {
        return serialConnection;
    }

    @Override
    public void update() {
        this.bluetoothCallback.onBluetoothReceive(listenForSignal());
    }
}
