package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.NotificationsSystem;
import Utils.Updatable;

import java.awt.*;
import java.util.ArrayList;

//TODO: Call this method whenever a connection has been interrupted.
public class ConnectionLostNotification extends NotificationsSystem implements Updatable, NotificationInterface {

    private int blinkTime = 250;

    public ConnectionLostNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.red;
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

            //TODO: Create a jingle for this and play this whenever the method is called OR beep it at a unique frequency.
            for (Buzzer buzzer : this.buzzers){
                buzzer.buzz(1000, this.notePitchGenerator.getNote("C", 3));
            }

            for (int i = 0; i < 6; i++){
                NeoPixelLed neoPixelLed = this.neoPixelLeds.get(i);

                if (this.lightColorPattern.charAt(i) == 'A'){
                    neoPixelLed.on();
                    neoPixelLed.setColor(neoPixelLedColorA);
                    neoPixelLed.blink(blinkTime);
                } else if (this.lightColorPattern.charAt(i) == 'B'){
                    neoPixelLed.on();
                    neoPixelLed.setColor(neoPixelLedColorB);
                    neoPixelLed.blink(blinkTime);
                }
            }
        }
    }
}
