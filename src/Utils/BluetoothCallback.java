package Utils;

import Hardware.BluetoothReceiver;

/**
 * Callback for the BluetoothReceiver class.
 * @see BluetoothReceiver
 * @version 1.0
 */
public interface BluetoothCallback {
    /**
     * TODO WRITE THIS PLEASE
     * @param commands TODO WRITE THIS PLEASE
     */
    void onBluetoothReceive(BluetoothReceiver.Commands commands);
}
