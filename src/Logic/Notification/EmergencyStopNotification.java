package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;

import java.awt.*;
import java.util.ArrayList;

/**
 * Notification for when the Emergency break is activated. Extends <code>AbstractNotification</code>.
 * @see AbstractNotification
 * @author Berend de Groot
 * @version 1.2
 */
public class EmergencyStopNotification extends AbstractNotification {

    /**
     * Constructor for this notification.
     * Uses super, and refers to <code>AbstractNotification</code> with it.
     * @param buzzer Takes an instance of <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a> as a parameter.
     * @param neoPixelLeds Takes an ArrayList of <a href="{@docRoot}/Hardware/NeoPixelLed.html">NeoPixelLed</a>s as a parameter.
     * @see AbstractNotification#AbstractNotification(Buzzer, ArrayList)
     */
    public EmergencyStopNotification(Buzzer buzzer, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzer, neoPixelLeds);
        this.neoPixelLedColorA = Color.red;
    }

    /**
     * Overrides <code>notificationSpecificMethod()</code> in <code>AbstractNotification</code>.
     * Abstract method all notifications have. This method contain instructions for the <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a> and <a href="{@docRoot}/Hardware/NeoPixelLed.html">NeoPixelLed</a>s on the BoeBot.
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        this.getBuzzer().buzz(1000, this.notePitchGenerator.getNote("C#", 5));

        for (NeoPixelLed neoPixelLed : this.getNeoPixelLeds()){
            neoPixelLed.setColor(this.neoPixelLedColorA);
            neoPixelLed.on();
        }
    }
}
