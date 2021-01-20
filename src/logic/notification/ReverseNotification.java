package logic.notification;

import hardware.Buzzer;
import hardware.NeoPixelLed;
import java.awt.*;
import java.util.ArrayList;

/**
 * notification for when the BoeBot is driving backwards. Extends <code>AbstractNotification</code>.
 * @author Berend de Groot
 * @version 1.3
 * @see AbstractNotification
 */
public class ReverseNotification extends AbstractNotification {

    /**
     * Constructor for this notification.
     * Uses super, and refers to <code>AbstractNotification</code> with it.
     * @param buzzer Takes an instance of <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a> as a parameter.
     * @param neoPixelLeds Takes an ArrayList of <a href="{@docRoot}/hardware/NeoPixelLed.html">NeoPixelLed</a>s as a parameter.
     * @see AbstractNotification#AbstractNotification(Buzzer, ArrayList)
     */
    public ReverseNotification(Buzzer buzzer, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzer, neoPixelLeds);
        this.neoPixelLedColorA = Color.orange;
        //Only uses the lights on the sides to simulate garbage truck lights.
        this.setLightColorPattern("AXAAXA");
    }

    /**
     * Overrides <code>notificationSpecificMethod()</code> in <code>AbstractNotification</code>.
     * Abstract method all notifications have. This method contain instructions for the <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a> and <a href="{@docRoot}/hardware/NeoPixelLed.html">NeoPixelLed</a>s on the BoeBot.
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        this.setBlinkTime(250);
        this.getBuzzer().buzz(1000, 500);
        this.useLightsBasedOnString();
    }
}
