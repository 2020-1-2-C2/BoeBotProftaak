package Logic;

import Hardware.BluetoothReceiver;
import Utils.BlueToothControllerCallback;
import Utils.BluetoothCallback;
import Utils.Updatable;

/**
 * @author Martijn de Kam, Berend de Groot
 */
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

    /**
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * Runs the <code>update()</code> method in <a href="{@docRoot}/Hardware/BluetoothReceiver.html">BluetoothReceiver</a>.
     * @see Updatable#update()
     * @see BluetoothReceiver#update()
     */
    @Override
    public void update() {
        this.bluetoothReceiver.update();
    }

    /**
     * Auto-generated getter for <code>this.bluetoothReceiver</code>.
     * @return this.bluetoothReceiver
     */
    public BluetoothReceiver getBluetoothReceiver() {
        return this.bluetoothReceiver;
    }
}
