package Utils;

/**
 * Interface to allow a callback on pressing an infrared remote button.
 * This interface is used by the InfraredReceiver component in <a href="{@docRoot}/Hardware/InfraredReceiver.html">InfraredReceiver</a>.
 * @version 1.0
 * @author Berend de Groot, Martijn de Kam, Meindert Kempe
 */
public interface InfraredCallback {
    /**
     * TODO WRITE PLEASE
     * @param button TODO WRITE PLEASE
     */
    void onInfraredButton(int button);
}
