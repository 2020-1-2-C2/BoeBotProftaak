package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.NotificationsSystem;
import TI.Timer;
import Utils.Updatable;

import java.awt.*;
import java.util.ArrayList;

//TODO: Remove unnecessary code.
//TODO: Call this during an emergency break.
public class EmergencyBreakNotification extends NotificationsSystem implements Updatable, NotificationInterface{

    private int blinkTime = 10;

    public EmergencyBreakNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.red;
        this.lightColorPattern = "AAAAAA";
        this.setNotificationActive(false);

        for (NeoPixelLed neoPixelLed : neoPixelLeds){ //TODO: Check why this is not working
            neoPixelLed.setBlinkingTimer(this.blinkTime);
        }

    }

    @Override
    public void notificationSpecificMethod(){
        if (this.isNotificationActive){

            for (Buzzer buzzer : this.buzzers){
                buzzer.buzz(1000, this.notePitchGenerator.getNote("C#", 5));
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
                } else if (this.lightColorPattern.charAt(i) == 'X'){ //TODO: Check whether this messes with the other on/off code.
                    neoPixelLed.off();
                }
            }
        }

    }
}
