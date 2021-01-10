package Logic;

import Hardware.BluetoothReceiver;
import Utils.BlueToothControllerCallback;
import Utils.BluetoothCallback;
import Utils.Updatable;

public class BluetoothController implements Updatable, BluetoothCallback {

    private BlueToothControllerCallback blueToothControllerCallback;
    private BluetoothReceiver bluetoothReceiver;

    public BluetoothController(BlueToothControllerCallback blueToothControllerCallback) {
        this.blueToothControllerCallback = blueToothControllerCallback;
        this.bluetoothReceiver = new BluetoothReceiver(this);
    }

    @Override
    public void onBluetoothReceive(BluetoothReceiver.Commands commands) {
        this.blueToothControllerCallback.onBlueToothControllerCommand(commands);
    }

    @Override
    public void update() {
        this.bluetoothReceiver.update();
    }

    public BluetoothReceiver getBluetoothReceiver() {
        return bluetoothReceiver;
    }
}
