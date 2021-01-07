package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;

import java.awt.*;
import java.util.ArrayList;

//TODO: Make class

/**
 * Notification for when a button on the remote is pressed. Extends AbstractNotification.
 * @see AbstractNotification
 * @author Berend de Groot
 * @version 1.1
 */
public class RemoteNotification extends AbstractNotification{


    public RemoteNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.CYAN;
        for (NeoPixelLed neoPixelLed : this.neoPixelLeds){
            neoPixelLed.setBlinkingTimer(1);
        }
    }

    @Override
    public void notificationSpecificMethod() {
        for (NeoPixelLed neoPixelLed : this.neoPixelLeds){
            neoPixelLed.on();
            neoPixelLed.setColor(neoPixelLedColorA);
        }
    }
}
