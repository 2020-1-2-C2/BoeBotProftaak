package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.Jingle;

import java.awt.*;
import java.util.ArrayList;

/**
 * Notification for when connection to the software is lost.
 */
public class DisconnectedNotification extends AbstractNotification {


    public DisconnectedNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.red;
        this.neoPixelLedColorB = Color.yellow;
        this.lightColorPattern = "ABABAB";
    }

    @Override
    public void notificationSpecificMethod() {
        System.out.println("DisonnectedNotification notificationSpecificMethod() has been called");
        int blinkTime = 250;

        this.getBuzzers().get(0).playSong(new Jingle().somebodyThatIUsedToKnow());

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
            } else if (this.lightColorPattern.charAt(i) == 'X') { //TODO: Check whether this messes with the other on/off code.
                neoPixelLed.off();
            }
        }
    }

    @Override
    public void update() {
        this.notificationSpecificMethod();
    }
}
