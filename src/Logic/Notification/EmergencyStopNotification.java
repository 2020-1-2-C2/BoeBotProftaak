package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;

import java.awt.*;
import java.util.ArrayList;

/**
 * Notification for when the Emergency break is activated. Extends AbstractNotification.
 * @see AbstractNotification
 * @author Berend de Groot
 * @version 1.0
 */
public class EmergencyStopNotification extends AbstractNotification {

    /**
     * Constructor for this notification.
     * Uses super, and refers to AbstractNotification with it.
     * @param buzzers Takes an arraylist of Buzzers as a parameter.
     * @param neoPixelLeds Takes an arraylist of NeoPixelLeds as a parameter.
     * @see AbstractNotification#AbstractNotification(ArrayList, ArrayList)
     */
    public EmergencyStopNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.red;
    }

    /**
     * Overrides notificationSpecificMethod() in AbstractNotification.java.
     * Abstract method all notifications have. This method contain instructions for the Buzzer and NeoPixelLeds on the BoeBot.
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        System.out.println("EmergencyStopNotification notificationSpecificMethod() has been called");
        this.buzzers.get(0).buzz(1000, this.notePitchGenerator.getNote("C#", 5));

        for (NeoPixelLed neoPixelLed : this.neoPixelLeds){
            neoPixelLed.setColor(this.neoPixelLedColorA);
            neoPixelLed.on();
        }
    }
}
