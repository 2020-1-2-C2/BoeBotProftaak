package logic.notification;

import hardware.Buzzer;
import hardware.NeoPixelLed;
import logic.Jingle;

import java.awt.*;
import java.util.ArrayList;

/**
 * notification that plays whenever a route is missing in <code>DriveSystem</code>. Extends <code>AbstractNotification</code>.
 *
 * @author Berend de Groot
 * @version 1.2
 * @see AbstractNotification#AbstractNotification(Buzzer, ArrayList)
 */
public class MissingRouteNotification extends AbstractNotification {
    /**
     * Constructor for the <code>MissingRouteNotification</code> class.
     * @param buzzer      Takes in an instance of <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a> to handle the sound.
     * @param neoPixelLeds Takes in an ArrayList of <a href="{@docRoot}/hardware/NeoPixelLed.html">NeoPixelLed</a>s to handle the lights.
     * @see Buzzer
     * @see NeoPixelLed
     */
    public MissingRouteNotification(Buzzer buzzer, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzer, neoPixelLeds);
        this.neoPixelLedColorA = Color.PINK;
        this.neoPixelLedColorB = Color.red;
        this.setLightColorPattern("ABABAB");
        this.setDisableAfterTime(5000);
    }

    /**
     * Overrides <code>notificationSpecificMethod()</code> in <code>AbstractNotification</code>.
     * Abstract method all notifications have. This method contain instructions for the
     * <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a> and <a href="{@docRoot}/hardware/NeoPixelLed.html">NeoPixelLed</a>s on the BoeBot.
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        this.setBlinkTime(250);
        //TODO: Add Jingle for missing route.
        this.getBuzzer().playSong(new Jingle().somebodyThatIUsedToKnow());
        this.useLightsBasedOnString();
    }
}
