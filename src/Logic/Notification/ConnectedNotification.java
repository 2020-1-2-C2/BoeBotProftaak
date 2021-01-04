package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.Jingle;

import java.awt.*;
import java.util.ArrayList;

public class ConnectedNotification extends AbstractNotification {


    public ConnectedNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.green;
        this.neoPixelLedColorB = Color.yellow;
        this.lightColorPattern = "ABABAB";
    }

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

    @Override
    public void update() {
        this.notificationSpecificMethod();
    }
}
