package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.NotificationsSystem;
import Utils.Updatable;

import java.awt.*;
import java.util.ArrayList;

public class DrivingBackwardsNotification extends NotificationsSystem implements Updatable, NotificationInterface {

    private int blinkTime = 250;

    public DrivingBackwardsNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);

        this.neoPixelLedColorA = Color.orange;
        this.lightColorPattern = "AXAAXA"; //Only uses the lights on the sides to simulate garbage truck lights
    }

    public void notificationSpecificMethod(){
        if (this.isNotificationActive){
            for (Buzzer buzzer : this.buzzers){
                buzzer.buzz(400, 1000);
            }

            for (int i = 0; i < 6; i++){
                NeoPixelLed neoPixelLed = this.neoPixelLeds.get(i);

                if (this.lightColorPattern.charAt(i) == 'A'){
                    neoPixelLed.on();
                    neoPixelLed.setColor(neoPixelLedColorA);
                    neoPixelLed.blink(blinkTime);
                } else if (this.lightColorPattern.charAt(i) == 'X'){ //TODO: Check whether this messes with the other on/off code.
                    neoPixelLed.off();
                }
            }
        }

    }
}
