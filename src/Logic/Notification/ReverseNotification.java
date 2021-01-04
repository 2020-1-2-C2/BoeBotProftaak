package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.Jingle;

import java.awt.*;
import java.util.ArrayList;

public class ReverseNotification extends AbstractNotification {


    public ReverseNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.orange;
        this.lightColorPattern = "AXAAXA"; //Only uses the lights on the sides to simulate garbage truck lights
    }

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

    @Override
    public void update() {
        this.notificationSpecificMethod();
    }
}
