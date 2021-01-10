package Logic;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Utils.Updatable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages the hardware updatable objects that are part of the notification system.
 */
public class NotificationSystemController implements Updatable {

    private ArrayList<Updatable> updatables;

    private ArrayList<NeoPixelLed> neoPixelLeds;
    private Buzzer buzzer;

    public NotificationSystemController() {
        this.updatables = new ArrayList<>();
        this.neoPixelLeds = new ArrayList<>();
        this.buzzer = new Buzzer(Configuration.buzzerPinId);

        // Filling the NeoPixelLeds ArrayList.
        Collections.addAll(this.neoPixelLeds, new NeoPixelLed(0), new NeoPixelLed(1), new NeoPixelLed(2),
                new NeoPixelLed(3), new NeoPixelLed(4), new NeoPixelLed(5));

        // Adding the NeoPixelLeds and buzzer to the updatables ArrayList.
        this.updatables.addAll(this.neoPixelLeds);
        this.updatables.add(this.buzzer);
    }

    public ArrayList<NeoPixelLed> getNeoPixelLeds() {
        return this.neoPixelLeds;
    }

    public Buzzer getBuzzer() {
        return this.buzzer;
    }

    @Override
    public void update() {
        for (Updatable u : this.updatables) {
            u.update();
        }
    }
}
