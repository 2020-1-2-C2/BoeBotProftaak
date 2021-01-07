package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.Jingle;

import java.awt.*;
import java.util.ArrayList;

/**
 * Notification for when connection to the software has been established. Extends AbstractNotification.
 * @author Berend de Groot
 * @version 1.0
 * @see AbstractNotification
 */
public class ConnectedNotification extends AbstractNotification {

    /**
     * Constructor for this notification.
     * Uses super, and refers to AbstractNotification with it.
     * @param buzzers Takes an arraylist of Buzzers as a parameter.
     * @param neoPixelLeds Takes an arraylist of NeoPixelLeds as a parameter.
     * @see AbstractNotification#AbstractNotification(ArrayList, ArrayList)
     */
    public ConnectedNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.green;
        this.neoPixelLedColorB = Color.yellow;
        this.lightColorPattern = "ABABAB";
    }

    /**
     * Overrides notificationSpecificMethod() in AbstractNotification.java.
     * Abstract method all notifications have. This method contain instructions for the Buzzer and NeoPixelLeds on the BoeBot.
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        System.out.println("ConnectedNotification notificationSpecificMethod() has been called");
        int blinkTime = 250;

        this.getBuzzers().get(0).playSong(new Jingle().brotherJohn());

        for (int i = 0; i < 6; i++) {
            NeoPixelLed neoPixelLed = this.neoPixelLeds.get(i);

            if (this.lightColorPattern.charAt(i) == 'A') {
                neoPixelLed.on();
                neoPixelLed.setColor(this.neoPixelLedColorA);
                neoPixelLed.blink(blinkTime);
            } else if (this.lightColorPattern.charAt(i) == 'B') {
                neoPixelLed.on();
                neoPixelLed.setColor(this.neoPixelLedColorB);
                neoPixelLed.blink(blinkTime);
            }
        }
    }
}
