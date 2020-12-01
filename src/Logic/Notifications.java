package Logic;

import Hardware.Buzzer;
import Hardware.RGB;
import TI.Timer;
import Utils.Led;
import Utils.Updatable;

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


        buzzerTimer.setInterval(this.buzzerInterval);

    }

    public void remoteNotification(){
        this.timerIsEnabled = false;

        for (Led led : leds) {
            if (led instanceof RGB) {
                ((RGB) led).setColor(true, false, false);
            }

            led.blink(ledInterval);
        }
    }

    @Override
    public void update() {
        if (buzzerTimer.timeout() && timerIsEnabled) {
            for (Buzzer buzzer : buzzers) {
                buzzer.buzz(buzzerTime, buzzerFrequency);
            }
        }
        if (ledTimer.timeout() && timerIsEnabled)
        for (Led led : leds) {
            if (led instanceof RGB) {
                ((RGB) led).setColor(true, false, false);
            }
            else{
                led.on();
            }

        }
    }
}
