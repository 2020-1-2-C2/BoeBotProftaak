package Utils;

/**
 * Interface to allow a callback on pressing an infrared remote button.
 * This interface is used by the InfraredReceiver component in InfraredReceiver.java.
 * @version 1.0
 */
public interface InfraredCallback {
    /**
     * TODO WRITE PLEASE
     * @param button TODO WRITE PLEASE
     */
    void onInfraredButton(int button);
}
