package Utils;

import Hardware.BluetoothReceiver;

/**
 * Interface for the BluetoothCallback interface which acts as a pass through.
 * @author Martijn de Kam
 */
public interface BlueToothControllerCallback {
    void onBlueToothControllerCommand(BluetoothReceiver.Commands command);
}
