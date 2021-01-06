package Utils;

import Hardware.BluetoothReceiver;

/**
 * Callback for the <a href="{@docRoot}/Hardware/BluetoothReceiver.html">BluetoothReceiver class</a>.
 * @see BluetoothReceiver
 * @version 1.0
 */
public interface BluetoothCallback {
    /**
     * Method that is used to receive the bluetooth commands.
     * Commands zijn beschreven in BlueToothReceiver.
     */
    void onBluetoothReceive(BluetoothReceiver.Commands commands);
}
