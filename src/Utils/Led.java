package Utils;

//TODO: Update documentation on removal of the DefaultLed.java and RGBLed.java classes.

/**
 * Callback used by the NeoPixelLed, DefaultLed and RGBLed class.
 * This interface contains the basic functions these hardware components need.
 * @see Hardware.NeoPixelLed
 * @see Hardware.DefaultLed
 * @see Hardware.RGBLed
 * @version 1.0
 * @author Berend de Groot
 */
public interface Led extends Updatable {
    /**
     * Method called to turn the Led on.
     */
    void on();

    /**
     * Method called to turn the Led off.
     */
    void off();

    /**
     * Method called to make the Led fade.
     * @param fade Int specifying the color.
     */
    void fade(int fade);

    /**
     * Method called to make the Led blink. It does this by turning the Led on and off at a specified interval in the parameter.
     * @param interval Int containing the value of the timer.
     */
    void blink(int interval);

    /**
     * Man-made getter.
     * Method called to check whether the light is on or not.
     * @return Boolean; true or false.
     */
    boolean getIsOn();
}
