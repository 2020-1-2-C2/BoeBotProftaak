package logic;

import hardware.BluetoothReceiver;
import utils.BlueToothControllerCallback;
import utils.BluetoothCallback;
import utils.Updatable;

/**
 * Class which controls the BlueToothReceiver and sends the received signals to the IntelligentFoodAllocationDevice class
 *
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
        if (commands != BluetoothReceiver.Commands.DEFAULT) {
            System.out.println(commands.toString());
        }
        this.blueToothControllerCallback.onBlueToothControllerCommand(commands);
    }

    /**
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * Runs the <code>update()</code> method in <a href="{@docRoot}/hardware/BluetoothReceiver.html">BluetoothReceiver</a>.
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
