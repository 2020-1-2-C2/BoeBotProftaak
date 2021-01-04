package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;

import java.util.ArrayList;


//TODO: Look for an alternative system.
/**
 * Empty notification intended for temporary usage
 */
public class EmptyNotification extends AbstractNotification {


    public EmptyNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
    }

    @Override
    public void notificationSpecificMethod() {
    }

    @Override
    public void update() {
        System.out.println("EmptyNotification update() has been called");
    }
}
