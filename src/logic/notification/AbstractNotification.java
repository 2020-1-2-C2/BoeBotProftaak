package logic.notification;

import hardware.Buzzer;
import hardware.NeoPixelLed;
import logic.Configuration;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class used by the BoeBot for handling notifications. All notification classes extend this class, and thus inherit attributes.
 * Every notification has it's own unique <code>notificationSpecificMethod()</code> used to give instructions to the
 * <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a> and <a href="{@docRoot}/hardware/NeoPixelLed.html">NeoPixelLed</a>s.
 * @version 1.4
 * @author Berend de Groot
 */
public abstract class AbstractNotification {

    protected Buzzer buzzer;
    protected ArrayList<NeoPixelLed> neoPixelLeds;
    Color neoPixelLedColorA;
    Color neoPixelLedColorB;
    private String lightColorPattern;
    private int blinkTime;
    private int disableAfterTime;

    /**
     * Constructor for the <code>AbstractNotification</code> class.
     * @param buzzer Takes in an instance of <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a> to handle the sound.
     * @param neoPixelLeds Takes in an ArrayList of NeoPixelLeds to handle the lights.
     * @see Buzzer
     * @see NeoPixelLed
     */
    public AbstractNotification(Buzzer buzzer, ArrayList<NeoPixelLed> neoPixelLeds) {
        this.buzzer = buzzer;
        this.neoPixelLeds = neoPixelLeds;
    }

    /**
     * Abstract method all notifications have. Those methods contain instructions for the
     * <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a> and NeoPixelLeds on the BoeBot.
     * See classes in logic.notification for examples.
     * @see logic.notification.DisconnectedNotification
     */
    public abstract void notificationSpecificMethod();

    /**
     * Auto-generated getter for the <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a> instance.
     * @return this.buzzer, the <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a>.
     * @see Buzzer
     */
    public Buzzer getBuzzer() {
        return this.buzzer;
    }

    /**
     * Auto-generated getter for the <a href="{@docRoot}/hardware/NeoPixelLed.html">NeoPixelLed</a> ArrayList.
     * @return this.neoPixelLeds, the <a href="{@docRoot}/hardware/NeoPixelLed.html">NeoPixelLed</a> ArrayList.
     * @see NeoPixelLed
     */
    public ArrayList<NeoPixelLed> getNeoPixelLeds() {
        return this.neoPixelLeds;
    }

    /**
     * Auto-generated setter for the blinkTime attribute.
     * @param blinkTime Changes <code>this.blinkTime</code> to the parameter's value.
     */
    void setBlinkTime(int blinkTime) {
        this.blinkTime = blinkTime;
    }

    /**
     * Auto-generated setter for the lightColorPattern attribute.
     * @param lightColorPattern Changes <code>this.lightColorPattern</code> to the parameter's value.
     */
    void setLightColorPattern(String lightColorPattern) {
        this.lightColorPattern = lightColorPattern;
    }

    /**
     * Auto-generated getter for the disableAfterTime attribute.
     */
    public int getDisableAfterTime() {
        return this.disableAfterTime;
    }

    /**
     * Auto-generated setter for the disableAfterTime attribute.
     * @param disableAfterTime Changes <code>this.disableAfterTime</code> to the parameter's value.
     */
    public void setDisableAfterTime(int disableAfterTime) {
        this.disableAfterTime = disableAfterTime;
    }

    /**
     * Checks <code>this.lightColorPattern</code> and changes the <a href:"{@docRoot}/hardware/NeoPixelLed">NeoPixelLed</a>s accordingly. <p>
     * The properties of the Leds are based on the character in it's position (the first character is pinId(0), the second character is pinId(1) and so on)
     * and can have the following properties:
     * <ul>
     * <li>A: Sets the Led to <code>this.neoPixelLedColorA</code>.
     * <li>B: Sets the Led to <code>this.neoPixelLedColorB</code>.
     * <li>X: Turns the Led off.
     * </ul>
     */
    void useLightsBasedOnString() {
        for (int i = 0; i < Configuration.neoPixelLEDCount; i++) {
            NeoPixelLed neoPixelLed = this.getNeoPixelLeds().get(i);

            if (this.lightColorPattern.charAt(i) == 'A') {
                neoPixelLed.on();
                neoPixelLed.setColor(this.neoPixelLedColorA);
                neoPixelLed.blink(this.blinkTime);
            } else if (this.lightColorPattern.charAt(i) == 'B') {
                neoPixelLed.on();
                neoPixelLed.setColor(this.neoPixelLedColorB);
                neoPixelLed.blink(this.blinkTime);
            } else if (this.lightColorPattern.charAt(i) == 'X') {
                //TODO: Make sure the light is off and STAYS off.
                neoPixelLed.blink(0); //TODO: Remove once the bug is fixed.
                neoPixelLed.off();
            }
        }
    }

}
