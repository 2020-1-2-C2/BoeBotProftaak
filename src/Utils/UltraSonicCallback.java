package Utils;

/**
 * Interface to allow a callback on receiving an ultrasonic pulse
 */
public interface UltraSonicCallback {
    void onUltraSonicPulse(Integer distance);
}
