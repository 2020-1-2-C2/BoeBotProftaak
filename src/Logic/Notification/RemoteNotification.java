package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;

import java.awt.*;
import java.util.ArrayList;

//TODO: Make class

/**
 * Notification for when a button on the remote is pressed. Extends AbstractNotification.
 * @see AbstractNotification
 * @author Berend de Groot
 * @version 1.1
 */
public class RemoteNotification extends AbstractNotification{

    /**
     * Constructor for the RemoteNotification class. Extends AbstractNotification.
     * @param buzzers      Takes in an Arraylist of Buzzers to handle the sound.
     * @param neoPixelLeds Takes in an Arraylist of NeoPixelLeds to handle the lights.
     * @see Buzzer
     * @see NeoPixelLed
     */
    public RemoteNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.CYAN;
        for (NeoPixelLed neoPixelLed : this.neoPixelLeds){
            neoPixelLed.setBlinkingTimer(1);
        }
    }

    /**
     * Overrides notificationSpecificMethod() in AbstractNotification.java.
     * Abstract method all notifications have. This method contain instructions for the Buzzer and NeoPixelLeds on the BoeBot.
     * <p>
     * Turns every individual NeoPixelLed on and sets its color to <code>this.neoPixelLedColorA</code> (CYAN).
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        for (NeoPixelLed neoPixelLed : this.neoPixelLeds){
            neoPixelLed.on();
            neoPixelLed.setColor(neoPixelLedColorA);
        }
    }
}
