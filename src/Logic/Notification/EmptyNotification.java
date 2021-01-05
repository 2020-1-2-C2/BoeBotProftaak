package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;

import java.util.ArrayList;

/**
 * Empty notification intended to stop all ongoing notifications by replacing the original object, thus making it impossible for that object's code to run.
 * @author Berend de Groot
 * @version 1.0
 * @see AbstractNotification
 */
public class EmptyNotification extends AbstractNotification {

    /**
     * Constructor for this notification.
     * Uses super, and refers to AbstractNotification with it.
     * EmptyNotification is unique, since it's the only notification to only refer to AbstractNotification without setting attributes itself.
     * @param buzzers Takes an arraylist of Buzzers as a parameter.
     * @param neoPixelLeds Takes an arraylist of NeoPixelLeds as a parameter.
     * @see AbstractNotification#AbstractNotification(ArrayList, ArrayList)
     */
    public EmptyNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
    }

    /**
     * Overrides notificationSpecificMethod() in AbstractNotification.java.
     * Abstract method all notifications have. This method contain instructions for the Buzzer and NeoPixelLeds on the BoeBot.
     * This specific method turns the lights and the buzzer off, and is thus called EmptyNotification.java.
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        System.out.println("EmptyNotification notificationSpecificMethod() has been called");
        this.getBuzzers().get(0).off();
        for (NeoPixelLed neoPixelLed : this.neoPixelLeds){
            neoPixelLed.setShouldBeOn(false);
            neoPixelLed.off();
        }
    }
}
