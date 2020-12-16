package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.NotificationsSystem;
import Utils.Updatable;

import java.awt.*;
import java.util.ArrayList;

//TODO: Call this whenever there are no other notifications active.
public class NeutralNotification extends NotificationsSystem implements Updatable, NotificationInterface{

    public NeutralNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.green;
        this.lightColorPattern = "AAAAAA";
        this.setNotificationActive(true);
    }

    @Override
    public void notificationSpecificMethod(){
        if (this.isNotificationActive){
            for (int i = 0; i < 6; i++){
                NeoPixelLed neoPixelLed = this.neoPixelLeds.get(i);
                if (this.lightColorPattern.charAt(i) == 'A'){
                    neoPixelLed.on();
                    neoPixelLed.setColor(neoPixelLedColorA);
                }
            }
        }
    }
}
