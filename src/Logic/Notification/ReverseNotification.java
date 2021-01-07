package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;

import java.awt.*;
import java.util.ArrayList;

/**
 * Notification for when the BoeBot is driving backwards. Extends AbstractNotification.
 * @author Berend de Groot
 * @version 1.0
 * @see AbstractNotification
 */
public class ReverseNotification extends AbstractNotification {

    /**
     * Constructor for this notification.
     * Uses super, and refers to AbstractNotification with it.
     * @param buzzers Takes an arraylist of Buzzers as a parameter.
     * @param neoPixelLeds Takes an arraylist of NeoPixelLeds as a parameter.
     * @see AbstractNotification#AbstractNotification(ArrayList, ArrayList)
     */
    public ReverseNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.orange;
        this.lightColorPattern = "AXAAXA"; //Only uses the lights on the sides to simulate garbage truck lights
    }

    /**
     * Overrides notificationSpecificMethod() in AbstractNotification.java.
     * Abstract method all notifications have. This method contain instructions for the Buzzer and NeoPixelLeds on the BoeBot.
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        int blinkTime = 250;

        this.getBuzzers().get(0).buzz(1000, 1000);

        for (int i = 0; i < 6; i++) {
            NeoPixelLed neoPixelLed = this.neoPixelLeds.get(i);

            if (this.lightColorPattern.charAt(i) == 'A') {
                neoPixelLed.on();
                neoPixelLed.setColor(this.neoPixelLedColorA);
                neoPixelLed.blink(blinkTime);
            } else if (this.lightColorPattern.charAt(i) == 'X') { //TODO: Check whether this messes with the other on/off code.
                neoPixelLed.off();
            }
        }
    }
}
