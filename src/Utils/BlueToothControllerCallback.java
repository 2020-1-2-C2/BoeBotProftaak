package Utils;

import Hardware.BluetoothReceiver;

/**
 * @author Martijn de Kam
 */
public interface BlueToothControllerCallback {
    void onBlueToothControllerCommand(BluetoothReceiver.Commands command);
}
