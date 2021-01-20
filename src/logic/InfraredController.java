package logic;

import hardware.InfraredReceiver;
import utils.InfraredCallback;
import utils.InfraredControllerCallback;
import utils.Updatable;

/**
 * Controller for the InfraredReceiver class.
 * @author Martijn de Kam, Berend de Groot
 */
public class InfraredController implements Updatable, InfraredCallback {

    private InfraredReceiver infraredReceiver;
    private InfraredControllerCallback infraredControllerCallback;

    /**
     * Constructor for InfraredController class.
     */
    public InfraredController(InfraredControllerCallback infraredControllerCallback) {
        this.infraredReceiver = new InfraredReceiver(Configuration.infraredReceiverPinId, this);
        this.infraredControllerCallback = infraredControllerCallback;
    }

    /**
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * Runs the <code>update()</code> method in <a href="{@docRoot}/hardware/InfraredReceiver.html">InfraredReceiver</a>.
     * @see Updatable#update()
     * @see InfraredReceiver#update()
     */
    @Override
    public void update() {
        this.infraredReceiver.update();
    }

    /**
     * Receives the button pressed from the infrared receiver and sends it to the main class if the button is a valid button.
     * @param button Received binary code for the button pressed on the infrared remote.
     */
    @Override
    public void onInfraredButton(int button) {
        System.out.println("Infrared button: " + button);
        if (this.infraredReceiver.getPossibleButtons().contains(button)) {
            this.infraredControllerCallback.onInfraredControllerCommand(button);
        }
    }
}
