package logic.notification;

import hardware.Buzzer;
import hardware.NeoPixelLed;
import logic.Jingle;

import java.awt.*;
import java.util.ArrayList;

/**
 * notification for when connection to the software is lost. Extends <code>AbstractNotification</code>.
 * @author Berend de Groot
 * @version 1.3
 * @see AbstractNotification
 */
public class DisconnectedNotification extends AbstractNotification {

    /**
     * Constructor for this notification.
     * Uses super, and refers to <code>AbstractNotification</code> with it.
     * @param buzzer Takes an instance of <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a> as a parameter.
     * @param neoPixelLeds Takes an ArrayList of <a href="{@docRoot}/hardware/NeoPixelLed.html">NeoPixelLed</a>s as a parameter.
     * @see AbstractNotification#AbstractNotification(Buzzer, ArrayList)
     */
    public DisconnectedNotification(Buzzer buzzer, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzer, neoPixelLeds);
        this.neoPixelLedColorA = Color.red;
        this.neoPixelLedColorB = Color.yellow;
        this.setLightColorPattern("ABABAB");
        this.setDisableAfterTime(2000);
    }

    /**
     * Overrides <code>notificationSpecificMethod()</code> in <code>AbstractNotification</code>.
     * Abstract method all notifications have. This method contain instructions for the <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a> and <a href="{@docRoot}/hardware/NeoPixelLed.html">NeoPixelLed</a>s on the BoeBot.
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        System.out.println("HAHAHAHAH");
        this.setBlinkTime(250);
        this.getBuzzer().playSong(new Jingle().disconnectedJingle());
        this.useLightsBasedOnString();
    }
}
