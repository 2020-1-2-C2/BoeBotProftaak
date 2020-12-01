package Logic;

import Hardware.Buzzer;
import Hardware.Led;
import Hardware.RGB;
import TI.Timer;
import Utils.Updatable;

import java.util.ArrayList;

public class Notifications implements Updatable {

    private ArrayList<Buzzer> buzzers;
    private ArrayList<Led> leds;
    private Timer notificationTimer;

    public Notifications(ArrayList<Buzzer> buzzers, ArrayList<Led> leds) {
        this.buzzers = buzzers;
        this.leds = leds;
        notificationTimer = new Timer(1000);
    }

    public void emergencyNotification() {
        for (Led led : leds) {
            if (led instanceof RGB) {
                ((RGB) led).setColor(true, false, false);
            }
            //TODO maybe put everything in one timer.
            led.blink(500);
        }

        notificationTimer.setInterval(1000);

    }

    @Override
    public void update() {
        if (notificationTimer.timeout()) {
            for (Buzzer buzzer : buzzers) {
                buzzer.buzz(500, 400);
            }
        }
    }
}
