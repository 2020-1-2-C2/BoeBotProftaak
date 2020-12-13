package Utils;

import Hardware.BluetoothReceiver;

public interface BluetoothCallback {
    void onBluetoothReceive(BluetoothReceiver.Commands commands);
}
