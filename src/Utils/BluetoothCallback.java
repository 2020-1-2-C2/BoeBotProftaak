package Utils;

import Hardware.BluetoothReceiver;

/**
 * Callback for the <a href="{@docRoot}/Hardware/BluetoothReceiver.html">BluetoothReceiver</a> class.
 * @see BluetoothReceiver
 * @version 1.0
 * @author Berend de Groot, Lars hoendervangers
 */
public interface BluetoothCallback {
    /**
     * Method that is used to receive the bluetooth commands.
     * Commands-list are located in the BluetoothReceiver class.
     * @param commands The command it currently receives.
     */
    void onBluetoothReceive(BluetoothReceiver.Commands commands);
}
