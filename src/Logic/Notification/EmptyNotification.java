package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;

import java.util.ArrayList;

/**
 * Empty notification intended to stop all ongoing notifications by replacing the original object, thus making it impossible for that object's code to run.
 * Extends <code>AbstractNotification</code>.
 * @author Berend de Groot
 * @version 1.0
 * @see AbstractNotification
 */
public class EmptyNotification extends AbstractNotification {

    /**
     * Constructor for this notification.
     * Uses super, and refers to <code>AbstractNotification</code> with it.
     * <code>EmptyNotification</code> is unique, since it's the only notification to only refer to <code>AbstractNotification</code> without setting attributes itself.
     * @param buzzer Takes an instance of <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a> as a parameter.
     * @param neoPixelLeds Takes an ArrayList of <a href="{@docRoot}/Hardware/NeoPixelLed.html">NeoPixelLed</a>s as a parameter.
     * @see AbstractNotification#AbstractNotification(Buzzer, ArrayList)
     */
    public EmptyNotification(Buzzer buzzer, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzer, neoPixelLeds);
        for (NeoPixelLed neoPixelLed : this.getNeoPixelLeds()){
            neoPixelLed.setBlinkingTimer(1000);
            neoPixelLed.setShouldBeOn(false);
        }
    }

    /**
     * Overrides <code>notificationSpecificMethod()</code> in <code>AbstractNotification</code>.
     * Abstract method all notifications have. This method contain instructions for the <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a> and <a href="{@docRoot}/Hardware/NeoPixelLed.html">NeoPixelLed</a>s on the BoeBot.
     * This specific method turns the lights and the buzzer off, and is thus called <code>EmptyNotification</code>.
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        this.getBuzzer().off();
        for (NeoPixelLed neoPixelLed : this.getNeoPixelLeds()){
            neoPixelLed.setShouldBeOn(false);
            neoPixelLed.off();
        }
    }
}
