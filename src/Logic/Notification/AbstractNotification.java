package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.NoteLengthGenerator;
import Logic.NotePitchGenerator;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class used by the BoeBot for handling notifications. All notification classes extend this class, and thus inherit attributes.
 * Every notification has it's own unique <code>notificationSpecificMethod()</code> used to give instructions to the <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a> and <a href="{@docRoot}/Hardware/NeoPixelLed.html">NeoPixelLed</a>s.
 * @version 1.2
 * @author Berend de Groot
 */
public abstract class AbstractNotification {

    protected Buzzer buzzer;
    protected ArrayList<NeoPixelLed> neoPixelLeds;
    NotePitchGenerator notePitchGenerator = new NotePitchGenerator();
    Color neoPixelLedColorA;
    Color neoPixelLedColorB;
    private String lightColorPattern;
    private int blinkTime;

    /**
     * Constructor for the <code>AbstractNotification</code> class.
     * @param buzzer Takes in an instance of <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a> to handle the sound.
     * @param neoPixelLeds Takes in an ArrayList of NeoPixelLeds to handle the lights.
     * @see Buzzer
     * @see NeoPixelLed
     */
    public AbstractNotification(Buzzer buzzer, ArrayList<NeoPixelLed> neoPixelLeds) {
        this.buzzer = buzzer;
        this.neoPixelLeds = neoPixelLeds;
    }

    /**
     * Abstract method all notifications have. Those methods contain instructions for the <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a> and NeoPixelLeds on the BoeBot.
     * See classes in Logic.Notification for examples.
     * @see Logic.Notification.DisconnectedNotification
     */
    public abstract void notificationSpecificMethod();

    /**
     * Auto-generated getter for the <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a> instance.
     * @return this.buzzer, the <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a>.
     * @see Buzzer
     */
    public Buzzer getBuzzer() {
        return this.buzzer;
    }

    /**
     * Auto-generated getter for the <a href="{@docRoot}/Hardware/NeoPixelLed.html">NeoPixelLed</a> ArrayList.
     * @return this.neoPixelLeds, the <a href="{@docRoot}/Hardware/NeoPixelLed.html">NeoPixelLed</a> ArrayList.
     * @see NeoPixelLed
     */
    public ArrayList<NeoPixelLed> getNeoPixelLeds() {
        return this.neoPixelLeds;
    }

    /**
     * Auto-generated setter for the blinkTime attribute.
     * @param blinkTime Changes <code>this.blinkTime</code> to the parameter's value.
     */
    void setBlinkTime(int blinkTime){
        this.blinkTime = blinkTime;
    }

    /**
     * Auto-generated setter for the lightColorPattern attribute.
     * @param lightColorPattern Changes <code>this.lightColorPattern</code> to the parameter's value.
     */
    void setLightColorPattern(String lightColorPattern){
        this.lightColorPattern = lightColorPattern;
    }

    /**
     * Checks <code>this.lightColorPattern</code> and changes the <a href:"{@docRoot}/Hardware/NeoPixelLed">NeoPixelLed</a>s accordingly. <p>
     * The properties of the Leds are based on the character in it's position (the first character is pinId(0), the second character is pinId(1) and so on)
     * and can have the following properties:
     * <ul>
     * <li>A: Sets the Led to <code>this.neoPixelLedColorA</code>.
     * <li>B: Sets the Led to <code>this.neoPixelLedColorB</code>.
     * <li>X: Turns the Led off.
     * </ul>
     */
    void useLightsBasedOnString(){
        for (int i = 0; i < 6; i++) {
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
                neoPixelLed.off();
            }
        }
    }

}
