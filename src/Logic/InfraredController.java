package Logic;

import Hardware.InfraredReceiver;
import Utils.InfraredCallback;
import Utils.InfraredControllerCallback;
import Utils.Updatable;
import java.util.HashMap;

public class InfraredController implements Updatable, InfraredCallback {

    private InfraredReceiver infraredReceiver;
    private InfraredControllerCallback infraredControllerCallback;

    public InfraredController(InfraredControllerCallback infraredControllerCallback) {
        this.infraredReceiver = new InfraredReceiver(Configuration.infraredReceiverPinId, this);
        this.infraredControllerCallback = infraredControllerCallback;
    }

    @Override
    public void update() {
        this.infraredReceiver.update();
    }

    /**
     * Receives the button pressed from the infrared receiver and sends it to the main class if the button is a valid button
     * @param button received binary code for the button pressed on the infrared remote.
     */
    @Override
    public void onInfraredButton(int button) {
        if (this.infraredReceiver.getPossibleButtons().contains(button)) {
            this.infraredControllerCallback.onInfraredControllerCommand(button);
        }
    }
}
