package Utils;

/**
 * Interface to allow a callback on pressing an infrared remote button.
 * This interface is used by the InfraredReceiver component in <a href="{@docRoot}/Hardware/InfraredReceiver.html">InfraredReceiver</a>.
 * @version 1.0
 * @author Berend de Groot, Martijn de Kam, Meindert Kempe
 */
public interface InfraredCallback {
    /**
     * callback on an object that implements the InfraredCallback interface
     * is called upon when an object of InfraredReceiver receives an infrared signal
     *
     * @param button binary number that should correspond to one of the buttons on the infrared remote
     */
    void onInfraredButton(int button);
}
