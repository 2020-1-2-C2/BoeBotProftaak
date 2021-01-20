package utils;

/**
 * Interface to allow a callback on receiving an ultrasonic-pulse.
 * @see hardware.UltraSonicReceiver
 * @author Martijn de Kam, Berend de Groot
 * @version 1.0
 */
public interface UltraSonicCallback {
    /**
     * Callback on an object that implements the UltraSonicCallback interface.
     * Is called upon when an object of UltraSonicReceiver senses something.
     * @param distance distance in cm
     */
    void onUltraSonicPulse(Integer distance);
}
