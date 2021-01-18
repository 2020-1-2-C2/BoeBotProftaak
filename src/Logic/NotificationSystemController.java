package Logic;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.Notification.AbstractNotification;
import Logic.Notification.EmptyNotification;
import Logic.Notification.ReverseNotification;
import Utils.Updatable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages the hardware updatable objects that are part of the notification system.
 * @author Martijn de Kam, Berend de Groot
 * @version 1.3
 */
public class NotificationSystemController implements Updatable {

    private ArrayList<Updatable> updatables;

    private ArrayList<NeoPixelLed> neoPixelLeds;
    private Buzzer buzzer;
    private DriveSystem driveSystem;
    private boolean notificationActive;

    public NotificationSystemController(DriveSystem driveSystem) {
        this.updatables = new ArrayList<>();
        this.neoPixelLeds = new ArrayList<>();
        this.buzzer = new Buzzer(Configuration.buzzerPinId);
        this.driveSystem = driveSystem;

        // Filling the NeoPixelLeds ArrayList.
        Collections.addAll(this.neoPixelLeds, new NeoPixelLed(0), new NeoPixelLed(1), new NeoPixelLed(2),
                new NeoPixelLed(3), new NeoPixelLed(4), new NeoPixelLed(5));

        // Adding the NeoPixelLeds and buzzer to the updatables ArrayList.
        this.updatables.addAll(this.neoPixelLeds);
        this.updatables.add(this.buzzer);
    }

    /**
     * Auto-generated getter for <code>this.neoPixelLeds</code>.
     * @return this.neoPixelLeds
     */
    public ArrayList<NeoPixelLed> getNeoPixelLeds() {
        return this.neoPixelLeds;
    }

    /**
     * Auto-generated getter for <code>this.buzzer</code>.
     * @return this.buzzer
     */
    public Buzzer getBuzzer() {
        return this.buzzer;
    }

    /**
     * Used to execute <code>notificationsSpecificMethod()</code> for the notification given in its parameter. <p>
     * This method also sets <a href="{@docRoot}/Hardware/NeoPixelLed.html">NeoPixelLeds' setShouldBeOn()</a> to <i>true</i>.
     * @param notification Notification that should have its <code>notificationSpecificMethod()</code> executed.
     * @see AbstractNotification#notificationSpecificMethod()
     * @see NeoPixelLed#setShouldBeOn(boolean)
     */
    public void setNotification(AbstractNotification notification) {
        this.notificationActive = notification instanceof EmptyNotification;

        for (NeoPixelLed neoPixelLed : this.getNeoPixelLeds()) {
            neoPixelLed.setShouldBeOn(true);
        }
        notification.notificationSpecificMethod();
    }

    /**
     * Runs <code>update()</code> all <b>this.</b>updatables. <p>
     * This method also checks whether the BoeBot is driving (<code>this.drivesystem.getDirection()</code>) backwards,
     * and if it is, it sets (<code>this.setNotification</code>) an
     * <a href="{@docRoot}/Logic/Notification/EmptyNotification.html">EmptyNotification</a> as the current active notification.
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * @see #setNotification(AbstractNotification)
     * @see Updatable#update()
     */
    @Override
    public void update() {
        for (Updatable u : this.updatables) {
            u.update();
        }

        //Handling the DriveSystem notifications.
        if (this.driveSystem.getDirection() == DriveSystem.BACKWARD){
            this.setNotification(new ReverseNotification(this.getBuzzer(), this.getNeoPixelLeds()));
        } else if (this.driveSystem.getDirection() == DriveSystem.FORWARD){
            this.setNotification(new EmptyNotification(this.getBuzzer(), this.getNeoPixelLeds()));
        }
    }
}
