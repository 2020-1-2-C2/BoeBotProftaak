package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import java.awt.*;
import java.util.ArrayList;

/**
 * Notification for when a button on the remote is pressed. Extends <code>AbstractNotification</code>.
 * @see AbstractNotification
 * @author Berend de Groot
 * @version 1.3
 */
public class RemoteNotification extends AbstractNotification{

    /**
     * Constructor for the <code>RemoteNotification</code> class. Extends <code>AbstractNotification</code>.
     * @param buzzer      Takes in an instance of <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a> to handle the sound.
     * @param neoPixelLeds Takes in an ArrayList of <a href="{@docRoot}/Hardware/NeoPixelLed.html">NeoPixelLed</a>s to handle the lights.
     * @see Buzzer
     * @see NeoPixelLed
     */
    public RemoteNotification(Buzzer buzzer, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzer, neoPixelLeds);
        this.neoPixelLedColorA = Color.CYAN;
    }

    /**
     * Overrides <code>notificationSpecificMethod()</code> in <code>AbstractNotification</code>.
     * Abstract method all notifications have. This method contain instructions for the <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a> and <a href="{@docRoot}/Hardware/NeoPixelLed.html">NeoPixelLed</a>s on the BoeBot.
     * <p>
     * Turns every individual <code>NeoPixelLed</code> on and sets its color to <code>this.neoPixelLedColorA</code> (CYAN).
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        for (NeoPixelLed neoPixelLed : this.getNeoPixelLeds()){
            neoPixelLed.on();
            neoPixelLed.setColor(this.neoPixelLedColorA);
            neoPixelLed.setBlinkingTimer(1);
        }
    }
}
