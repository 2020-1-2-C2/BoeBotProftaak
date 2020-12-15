package Logic;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import TI.Timer;
import Utils.Updatable;

import java.awt.*;
import java.util.ArrayList;


//TODO: Test all functionality
//TODO: Optimize code
//TODO: Check styling guide and use it as reference
//TODO: Check documentation and explain methods better
//TODO: Remove unnecessary comments.

/**
 * This class contains all notifications used by the BoeBot. These notifications use the buzzer to play sounds and the NeoPixelLeds to emit light.
 */
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
    private Color neoPixelLedColorA;
    private Color neoPixelLedColorB;
    private boolean neoPixelLedState = false;
    private String lightColorPattern;


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
        this.neoPixelLedColorA = Color.red;
        this.lightColorPattern = "AAAAAA";

        buzzerTimer.setInterval(this.buzzerInterval);
        neoPixelLedTimer.setInterval(this.neoPixelLedInterval);
    }


    /**
     * Method called everytime a button is pressed on the remote in RobotMain.java OnInfraredButton()
     */
    //TODO: Decide whether to use C5 or B4 (C5 is being used for the connection notification)
    public void remoteNotification(){
        this.buzzerNote = "B";
        this.buzzerOctave = 4;
        this.buzzerTime = 200;
        this.useSpecificFrequency = false;
        this.neoPixelLedInterval = 250;
        this.buzzerInterval = 1000;
        this.timerIsEnabled = true;
        this.repeat = false;
        this.neoPixelLedState = false;
        this.neoPixelLedColorA = Color.green;
        this.lightColorPattern = "AAAAAA";

        buzzerTimer.setInterval(this.buzzerInterval);
        neoPixelLedTimer.setInterval(this.neoPixelLedInterval);
    }

    /**
     * Method called when driving backwards. Frequency is based on sources available on this page: https://en.wikipedia.org/wiki/Back-up_beeper
     */
    public void drivingBackwardsNotification(){
        this.buzzerFrequency = 1000;
        this.buzzerTime = 200;
        this.useSpecificFrequency = true;
        this.neoPixelLedInterval = 250;
        this.buzzerInterval = 400; //TODO: Find a value used in everyday life for similar notification-systems.
        this.timerIsEnabled = true;
        this.repeat = true;
        this.neoPixelLedState = true;
        this.neoPixelLedColorA = Color.orange;
        this.lightColorPattern = "AXAAXA"; //Only uses the lights on the sides to simulate garbage truck lights

        buzzerTimer.setInterval(this.buzzerInterval);
        neoPixelLedTimer.setInterval(this.neoPixelLedInterval);
    }

    //Following methods are used to indicate client connection.
    //Client connection notifications ALL use a yellow light to inform the user it displays the client connection.

    /**
     * Method called whenever the connection to the PC hsa successfully been established.
     * TODO: Create a jingle ?
     * TODO: CALL THIS METHOD WHENEVER THE CONNECTION THE THE PC IS SUCCESSFUL
     */
    public void connectionSuccesNotification(){
        this.buzzerNote = "C";
        this.buzzerOctave = 5;
        this.buzzerTime = 1500;
        this.useSpecificFrequency = false;
        this.neoPixelLedInterval = 1500; //TODO: Find optimal value for this
        this.buzzerInterval = 1500;
        this.timerIsEnabled = true;
        this.repeat = false; //Wouldn't make sense to repeat this. DON'T CHANGE THIS BEFORE TESTING
        this.neoPixelLedState = false;
        this.neoPixelLedColorA = Color.green;
        this.neoPixelLedColorB = Color.yellow;
        this.lightColorPattern = "ABABAB";

        buzzerTimer.setInterval(this.buzzerInterval);
        neoPixelLedTimer.setInterval(this.neoPixelLedInterval);
    }


    /**
     * Method called whenever the connection to the PC is lost.
     * TODO: Create a jingle ?
     * TODO: CALL THIS METHOD WHENEVER THE CONNECTION THE THE PC IS LOST
     */
    public void connectionLostNotification(){
        this.buzzerNote = "C";
        this.buzzerOctave = 3;
        this.buzzerTime = 1000;
        this.useSpecificFrequency = false;
        this.neoPixelLedInterval = 250;
        this.buzzerInterval = 1000;
        this.timerIsEnabled = true;
        this.repeat = true;
        this.neoPixelLedState = false;
        this.neoPixelLedColorA = Color.red;
        this.neoPixelLedColorB = Color.yellow;
        this.lightColorPattern = "ABABAB";

        buzzerTimer.setInterval(this.buzzerInterval);
        neoPixelLedTimer.setInterval(this.neoPixelLedInterval);
    }

    @Override
    public void update() {
        //Buzzer related code
        if (buzzerTimer.timeout() && timerIsEnabled) {
            for (Buzzer buzzer : buzzers) {
                if (this.useSpecificFrequency){
                    buzzer.buzz(buzzerTime, buzzerFrequency);
                } else {
                    buzzer.buzz(buzzerTime, buzzerNote, buzzerOctave);
                }
            }
        }

        //NeoPixelLed old code
        //TODO: Delete this if the new code is functional
//        if (neoPixelLedTimer.timeout() && timerIsEnabled){
//            for (NeoPixelLed neoPixelLed : neoPixelLeds) {
//                if (!neoPixelLedState){
//                    if (neoPixelLed instanceof NeoPixelLed) {
//                        neoPixelLed.setColor(neoPixelLedColorA);
//                    } else {
//                        neoPixelLed.on();
//                    }
//                    neoPixelLedState = true;
//                } else {
//                    neoPixelLed.off();
//                    neoPixelLedState = false;
//                    if (!repeat) {
//                        timerIsEnabled = false;
//                    }
//                }
//            }
//        }

        //New NeoPixelLed system
        //TODO: Decide whether or not to use a for-loop instead of a for-each loop.
        if (neoPixelLedTimer.timeout() && timerIsEnabled){
            int i = 0;
            for (NeoPixelLed neoPixelLed : neoPixelLeds) {
                if (!neoPixelLedState){
                    if (neoPixelLed instanceof NeoPixelLed) { //TODO: Remove this check.
                        if (this.lightColorPattern.charAt(i) == 'A'){
                            neoPixelLed.setColor(neoPixelLedColorA);
                        } else if (this.lightColorPattern.charAt(i) == 'B'){
                            neoPixelLed.setColor(neoPixelLedColorB);
                        } else if (this.lightColorPattern.charAt(i) == 'X'){ //TODO: Check whether this messes with the other on/off code.
                            if (neoPixelLed.getIsOn()){ //TODO: Check whether this is optional.
                                neoPixelLed.off();
                            }
                        }
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
                i++;
            }
        }

    }
}
