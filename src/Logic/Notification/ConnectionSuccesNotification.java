package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.NotificationsSystem;
import Utils.Updatable;

import java.awt.*;
import java.util.ArrayList;

//TODO: Call this method whenever a successful connection has been established.
public class ConnectionSuccesNotification extends NotificationsSystem implements Updatable, NotificationInterface {

    private int blinkTime = 250;

    public ConnectionSuccesNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.green;
        this.neoPixelLedColorB = Color.yellow;
        this.lightColorPattern = "ABABAB";
        this.setNotificationActive(false);

        for (NeoPixelLed neoPixelLed : neoPixelLeds){ //TODO: Check why this is not working.
            neoPixelLed.setBlinkingTimer(this.blinkTime);
        }
    }

    @Override
    public void notificationSpecificMethod(){
        if (this.isNotificationActive){
            ////TODO: Create a jingle for this and play this whenever the method is called.
            for (Buzzer buzzer : this.buzzers){
                buzzer.buzz(1000, this.notePitchGenerator.getNote("C", 5));
            }

            for (int i = 0; i < 6; i++){
                NeoPixelLed neoPixelLed = this.neoPixelLeds.get(i);

                if (this.lightColorPattern.charAt(i) == 'A'){
                    neoPixelLed.on();
                    neoPixelLed.setColor(neoPixelLedColorA);
                } else if (this.lightColorPattern.charAt(i) == 'B'){
                    neoPixelLed.on();
                    neoPixelLed.setColor(neoPixelLedColorB);
                }
            }
        }
    }
}
