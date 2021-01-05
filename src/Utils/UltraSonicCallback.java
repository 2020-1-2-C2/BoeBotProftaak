package Utils;

//TODO: Be more specific
/**
 * Interface to allow a callback on receiving an ultrasonic pulse.
 * @see Hardware.UltraSonicReceiver
 */
public interface UltraSonicCallback {
    /**
     * TODO WRITE PLEASE
     * @param distance TODO WRITE PLEASE
     */
    void onUltraSonicPulse(Integer distance);
}
