package Utils;

/**
 * Interface to allow a callback on receiving an ultrasonic pulse.
 * @see Hardware.UltraSonicReceiver
 * @author Martijn de Kam, Berend de Groot
 * @version 1.0
 */
public interface UltraSonicCallback {
    /**
     * callback on an object that implements the UltraSonicCallback interface
     * is called upon when an object of UltraSonicReceiver senses something
     *
     * @param distance distance in cm
     */
    void onUltraSonicPulse(Integer distance);
}
