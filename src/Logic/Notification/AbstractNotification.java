package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.NoteLengthGenerator;
import Logic.NotePitchGenerator;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class used by the BoeBot for handling notifications. All notification classes extend this class, and thus inherit attributes.
 * Every notification has it's own unique notificationSpecificMethod() used to give instructions to the Buzzer and NeoPixelLeds.
 */
public abstract class AbstractNotification {

    //TODO: Check access-modifiers.
    protected ArrayList<Buzzer> buzzers;
    protected ArrayList<NeoPixelLed> neoPixelLeds;
    protected NotePitchGenerator notePitchGenerator = new NotePitchGenerator();
    protected NoteLengthGenerator noteLengthGenerator = new NoteLengthGenerator(128);
    protected Color neoPixelLedColorA;
    protected Color neoPixelLedColorB;
    protected String lightColorPattern;

    //TODO: Check access-modifiers.

    /**
     * Constructor for the AbstractNotification.java class.
     * @param buzzers Takes in an Arraylist of Buzzers to handle the sound.
     * @param neoPixelLeds Takes in an Arraylist of NeoPixelLeds to handle the lights.
     * @see Buzzer
     * @see NeoPixelLed
     */
    public AbstractNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        this.buzzers = buzzers;
        this.neoPixelLeds = neoPixelLeds;
    }

    /**
     * Abstract method all notifications have. Those methods contain instructions for the Buzzer and NeoPixelLeds on the BoeBot.
     * See classes in Logic.Notification for examples.
     * @see Logic.Notification.DisconnectedNotification
     */
    public abstract void notificationSpecificMethod();

    /**
     * Auto-generated getter for the Buzzer arraylist.
     * @return this.buzzers, the Buzzer arraylist.
     * @see Buzzer
     */
    public ArrayList<Buzzer> getBuzzers() {
        return this.buzzers;
    }

    //TODO: Remove if never used!
    /**
     * Auto-generated getter for the NeoPixelLed arraylist.
     * @return this.neoPixelLeds, the NeoPixelLed arraylist.
     * @see NeoPixelLed
     */
    public ArrayList<NeoPixelLed> getNeoPixelLeds() {
        return this.neoPixelLeds;
    }
}
