package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.Jingle;

import java.awt.*;
import java.util.ArrayList;

/**
 * Notification for when connection to the software is lost. Extends <code>AbstractNotification</code>.
 * @author Berend de Groot
 * @version 1.2
 * @see AbstractNotification
 */
public class DisconnectedNotification extends AbstractNotification {

    /**
     * Constructor for this notification.
     * Uses super, and refers to <code>AbstractNotification</code> with it.
     * @param buzzer Takes an instance of <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a> as a parameter.
     * @param neoPixelLeds Takes an ArrayList of <a href="{@docRoot}/Hardware/NeoPixelLed.html">NeoPixelLed</a>s as a parameter.
     * @see AbstractNotification#AbstractNotification(Buzzer, ArrayList)
     */
    public DisconnectedNotification(Buzzer buzzer, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzer, neoPixelLeds);
        this.neoPixelLedColorA = Color.red;
        this.neoPixelLedColorB = Color.yellow;
        this.setLightColorPattern("ABABAB");
    }

    /**
     * Overrides <code>notificationSpecificMethod()</code> in <code>AbstractNotification</code>.
     * Abstract method all notifications have. This method contain instructions for the <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a> and <a href="{@docRoot}/Hardware/NeoPixelLed.html">NeoPixelLed</a>s on the BoeBot.
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        this.setBlinkTime(250);
        this.getBuzzer().playSong(new Jingle().disconnectedJingle());
        this.useLightsBasedOnString();
    }
}
