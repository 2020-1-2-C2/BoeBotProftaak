package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;

import java.awt.*;
import java.util.ArrayList;

public class EmergencyStopNotification extends AbstractNotification {


    public EmergencyStopNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.red;
    }

    @Override
    public void notificationSpecificMethod() {
        this.buzzers.get(0).buzz(1000, this.notePitchGenerator.getNote("C#", 5));

        for (NeoPixelLed neoPixelLed : this.neoPixelLeds){
            neoPixelLed.setColor(this.neoPixelLedColorA);
            neoPixelLed.on();
        }
    }

    @Override
    public void update() {
        this.notificationSpecificMethod();
    }
}
