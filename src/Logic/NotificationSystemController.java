package Logic;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Utils.Updatable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages the hardware updatable objects that are part of the notification system
 */
public class NotificationSystemController implements Updatable {

    private ArrayList<Updatable> updatables;

    private ArrayList<NeoPixelLed> neoPixelLeds;
    private ArrayList<Buzzer> buzzers;

    public NotificationSystemController() {
        this.updatables = new ArrayList<>();
        this.neoPixelLeds = new ArrayList<>();
        this.buzzers = new ArrayList<>();

        // Filling the neopixels arraylist
        Collections.addAll(this.neoPixelLeds, new NeoPixelLed(0), new NeoPixelLed(1), new NeoPixelLed(2),
                new NeoPixelLed(3), new NeoPixelLed(4), new NeoPixelLed(5));

        // Filling the buzzers arraylist
        Collections.addAll(this.buzzers, new Buzzer(Configuration.buzzerPinId));

        // Adding the neopixels and buzzers to the updatables list
        for (NeoPixelLed neoPixelLed : this.neoPixelLeds) {
            this.updatables.add(neoPixelLed);
        }
        for (Buzzer buzzer : this.buzzers) {
            this.updatables.add(buzzer);
        }
    }

    public ArrayList<NeoPixelLed> getNeoPixelLeds() {
        return this.neoPixelLeds;
    }

    public ArrayList<Buzzer> getBuzzers() {
        return this.buzzers;
    }

    @Override
    public void update() {
        for (Updatable u : this.updatables) {
            u.update();
        }
    }
}
