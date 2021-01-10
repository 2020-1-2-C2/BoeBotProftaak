package Utils;

/**
 * Interface to allow a callback on receiving an ultrasonic pulse.
 * @see Hardware.UltraSonicReceiver
 * @author Martijn de Kam, Berend de Groot
 * @version 1.0
 */
public interface UltraSonicCallback {
    /**
     * TODO WRITE PLEASE
     * @param distance TODO WRITE PLEASE
     */
    void onUltraSonicPulse(Integer distance);
}
