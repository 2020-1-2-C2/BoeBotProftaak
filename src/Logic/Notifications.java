package Logic;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Hardware.RGBLed;
import TI.Timer;
import Utils.Led;
import Utils.Updatable;

import java.awt.*;
import java.util.ArrayList;

public class Notifications implements Updatable {

    private ArrayList<Buzzer> buzzers;
    private ArrayList<NeoPixelLed> neoPixelLeds;
    private Timer buzzerTimer;
    private Timer neoPixelLedTimer;
    private int buzzerTime;
    private int buzzerFrequency;
    private boolean useSpecificFrequency;
    private String buzzerNote;
    private int buzzerOctave;
    private int neoPixelLedInterval;
    private int buzzerInterval;
    private boolean timerIsEnabled = false;
    private boolean repeat = false;
    private Color neoPixelLedColor;
    private boolean neoPixelLedState = false;

    /**
     * Constructor for notifications.
     * @param buzzers Takes an arraylist of Buzzers
     * @param neoPixelLeds Takes an arraylist of NeoPixelLeds
     */
    public Notifications(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        this.buzzers = buzzers;
        this.neoPixelLeds = neoPixelLeds;
        buzzerTimer = new Timer(1000);
        neoPixelLedTimer = new Timer(1000);

    }

    /**
     * Method called in case of an emergency break in RobotMain.java onCollisionDetection()
     */
    public void emergencyNotification() {
        //this.buzzerFrequency = 400;
        this.buzzerNote = "A";
        this.buzzerOctave = 4;
        this.buzzerTime = 500;
        this.useSpecificFrequency = false;
        this.neoPixelLedInterval = 500;
        this.buzzerInterval = 1000;
        this.timerIsEnabled = true;
        this.repeat = true;
        this.neoPixelLedColor = Color.red;


        buzzerTimer.setInterval(this.buzzerInterval);
        neoPixelLedTimer.setInterval(this.neoPixelLedInterval);

    }


    /**
     * Method called everytime a button is pressed on the remote in RobotMain.java OnInfraredButton()
     */
    public void remoteNotification(){
        //this.buzzerFrequency = 400;
        this.buzzerNote = "C";
        this.buzzerOctave = 5;
        this.buzzerTime = 200;
        this.useSpecificFrequency = false;
        this.neoPixelLedInterval = 250;
        this.buzzerInterval = 1000;
        this.timerIsEnabled = true;
        this.repeat = false;
        this.neoPixelLedState = false;
        this.neoPixelLedColor = Color.green;

        buzzerTimer.setInterval(this.buzzerInterval);
        neoPixelLedTimer.setInterval(this.neoPixelLedInterval);
    }

    /**
     * Method called when driving backwards. Frequency is based on sources available on this page: https://en.wikipedia.org/wiki/Back-up_beeper
     */
    //TODO: Call method when driving backwards.
    //TODO: FIX THIS ENTIRE METHOD
    public void drivingBackwardsNotification(){
        this.buzzerFrequency = 1000;
        this.buzzerTime = 200;
        this.useSpecificFrequency = true;
        //this.neoPixelLedInterval = 250;
        this.buzzerInterval = 400; //TODO: Find a value used in everyday life for similar notification-systems.
        this.timerIsEnabled = true;
        this.repeat = true;
//        this.neoPixelLedState = true; //?
//        this.neoPixelLedColor = Color.orange;

        buzzerTimer.setInterval(this.buzzerInterval);
//        neoPixelLedTimer.setInterval(this.neoPixelLedInterval);

        for (NeoPixelLed neoPixelLed : this.neoPixelLeds){ //TODO: Write a suitable update method so we can use the blink() method in NeoPixelLed.java
            neoPixelLed.blink(400);
        }

    }

    /**
     * Method called whenever the connection to the PC is lost.
     * TODO: Create a jingle ?
     */
    public void connectionLostNotification(){
        this.buzzerNote = "C";
        this.buzzerOctave = 3;
        this.buzzerTime = 1000;
        this.useSpecificFrequency = true;
        this.neoPixelLedInterval = 250;
        this.buzzerInterval = 1000;
        this.timerIsEnabled = true;
        this.repeat = true;
        this.neoPixelLedState = true; //?
        this.neoPixelLedColor = Color.orange;

        buzzerTimer.setInterval(this.buzzerInterval);
        neoPixelLedTimer.setInterval(this.neoPixelLedInterval);
    }

    @Override
    public void update() {
        if (buzzerTimer.timeout() && timerIsEnabled) {
            for (Buzzer buzzer : buzzers) {
                if (this.useSpecificFrequency){
                    buzzer.buzz(buzzerTime, buzzerFrequency);
                } else {
                    buzzer.buzz(buzzerTime, buzzerNote, buzzerOctave);
                }
            }
        }
        if (neoPixelLedTimer.timeout() && timerIsEnabled){
            for (NeoPixelLed neoPixelLed : neoPixelLeds) {
                if (!neoPixelLedState){
                    if (neoPixelLed instanceof NeoPixelLed) {
                        neoPixelLed.setColor(neoPixelLedColor);
                    } else {
                        neoPixelLed.on();
                    }
                    neoPixelLedState = true;
                } else {
                    neoPixelLed.off();
                    neoPixelLedState = false;
                    if (!repeat) {
                        timerIsEnabled = false;
                    }
                }
            }
        }
    }
}
