package Logic;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.Notification.NotificationInterface;
import TI.BoeBot;
import Utils.Updatable;

import java.awt.*;
import java.util.ArrayList;

//TODO: Check why input detection stops working during a notification.
//TODO: STOP USING THIS CLASS. THIS WILL BE REMOVED SOON.
public class NotificationsSystem implements Updatable, NotificationInterface {

    //Protected so that Notification package can use this
    protected boolean isNotificationActive;
    protected ArrayList<Buzzer> buzzers;
    protected ArrayList<NeoPixelLed> neoPixelLeds;
    protected NotePitchGenerator notePitchGenerator = new NotePitchGenerator();
    protected NoteLengthGenerator noteLengthGenerator = new NoteLengthGenerator(128);
    protected Color neoPixelLedColorA;
    protected Color neoPixelLedColorB;
    protected String lightColorPattern;



    public NotificationsSystem(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        this.buzzers = buzzers;
        this.neoPixelLeds = neoPixelLeds;
    }

    @Override
    public void update() {
        BoeBot.wait(1);
        if (this.isNotificationActive){
            this.notificationSpecificMethod();
        } else {
            for (int i = 0; i < 6; i++){
                this.neoPixelLeds.get(i).setColor(Color.black);
            }
        }
    }

    public boolean isNotificationActive() {
        return this.isNotificationActive;
    }

    public void setNotificationActive(boolean notificationActive) {
        this.isNotificationActive = notificationActive;
    }

    public void notificationSpecificMethod(){
    }

    public ArrayList<Buzzer> getBuzzers() {
        return this.buzzers;
    }

    public ArrayList<NeoPixelLed> getNeoPixelLeds() {
        return this.neoPixelLeds;
    }
}
