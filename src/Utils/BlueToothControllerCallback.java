package Utils;

import Hardware.BluetoothReceiver;

public interface BlueToothControllerCallback {
    void onBlueToothControllerCommand(BluetoothReceiver.Commands command);
}
