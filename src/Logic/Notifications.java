package Logic;

import Hardware.Buzzer;
import Hardware.RGBLed;
import TI.Timer;
import Utils.Led;
import Utils.Updatable;

import java.awt.*;
import java.util.ArrayList;

public class Notifications implements Updatable {

    private ArrayList<Buzzer> buzzers;
    private ArrayList<Led> leds;
    private Timer buzzerTimer;
    private Timer ledTimer;
    private int buzzerTime;
    private int buzzerFrequency;
    private int ledInterval;
    private int buzzerInterval;
    private boolean timerIsEnabled = false;
    private boolean repeat = false;
    private Color ledColor;
    private boolean ledState = false;

    public Notifications(ArrayList<Buzzer> buzzers, ArrayList<Led> leds) {
        this.buzzers = buzzers;
        this.leds = leds;
        buzzerTimer = new Timer(1000);
        ledTimer = new Timer(1000);

    }

    public void emergencyNotification() {
        this.buzzerFrequency = 400;
        this.buzzerTime = 500;
        this.ledInterval = 500;
        this.buzzerInterval = 1000;
        this.timerIsEnabled = true;
        this.repeat = true;
        this.ledColor = Color.red;


        buzzerTimer.setInterval(this.buzzerInterval);
        ledTimer.setInterval(this.ledInterval);

    }

    public void remoteNotification(){
        this.buzzerFrequency = 400;
        this.buzzerTime = 500;
        this.ledInterval = 500;
        this.buzzerInterval = 1000;
        this.timerIsEnabled = true;
        this.repeat = false;
        this.ledState = false;
        this.ledColor = Color.green;

        buzzerTimer.setInterval(this.buzzerInterval);
        ledTimer.setInterval(this.ledInterval);
    }

    @Override
    public void update() {
        if (buzzerTimer.timeout() && timerIsEnabled) {
            for (Buzzer buzzer : buzzers) {
                buzzer.buzz(buzzerTime, buzzerFrequency);
            }
        }
        if (ledTimer.timeout() && timerIsEnabled){
            for (Led led : leds) {
                if (!ledState){
                    if (led instanceof RGBLed) {
                        ((RGBLed) led).setColor(ledColor);
                    }
                    else{
                        led.on();
                    }
                    ledState = true;
                } else {
                    led.off();
                    ledState = false;
                    if (!repeat) {
                        timerIsEnabled = false;
                    }
                }
            }
        }
    }
}
