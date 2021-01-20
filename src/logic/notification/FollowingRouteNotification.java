package logic.notification;

import hardware.Buzzer;
import hardware.NeoPixelLed;
import logic.Route;
import java.awt.*;
import java.util.ArrayList;

/**
 * Notification for when the bot is following a <a href="{@docRoot}/logic/Route.html"></a>. Extends <code>AbstractNotification</code>.
 * @see AbstractNotification
 * @see logic.Route
 * @see logic.DriveSystem#followRoute(Route)
 * @author Berend de Groot
 * @version 1.3
 */
public class FollowingRouteNotification extends AbstractNotification {
    /**
     * Constructor for the <code>AbstractNotification</code> class.
     * @param buzzer      Takes in an instance of <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a> to handle the sound.
     * @param neoPixelLeds Takes in an ArrayList of <a href="{@docRoot}/hardware/NeoPixelLed.html">NeoPixelLed</a>s to handle the lights.
     * @see Buzzer
     * @see NeoPixelLed
     */
    public FollowingRouteNotification(Buzzer buzzer, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzer, neoPixelLeds);
        this.neoPixelLedColorA = Color.DARK_GRAY;
        this.neoPixelLedColorB = Color.BLUE;
        this.setLightColorPattern("ABABAB");
        this.setBlinkTime(500);
    }

    /**
     * Overrides <code>notificationSpecificMethod()</code> in <code>AbstractNotification</code>.
     * Abstract method all notifications have. This method contain instructions for the <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a> and <a href="{@docRoot}/hardware/NeoPixelLed.html">NeoPixelLed</a>s on the BoeBot.
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        this.useLightsBasedOnString();
    }
}
