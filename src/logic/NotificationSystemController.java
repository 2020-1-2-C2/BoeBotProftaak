package logic;

import TI.Timer;
import hardware.Buzzer;
import hardware.NeoPixelLed;
import logic.notification.AbstractNotification;
import logic.notification.EmptyNotification;
import logic.notification.ReverseNotification;
import utils.Updatable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages the hardware updatable objects that are part of the notification system.
 * @author Martijn de Kam, Berend de Groot
 * @version 1.4
 */
public class NotificationSystemController implements Updatable {

    private ArrayList<Updatable> updatables;

    private ArrayList<NeoPixelLed> neoPixelLeds;
    private Buzzer buzzer;
    private DriveSystem driveSystem;
    private AbstractNotification notification;

    private Timer timer;
    private boolean timerIsEnabled;

    public NotificationSystemController(DriveSystem driveSystem) {
        this.updatables = new ArrayList<>();
        this.neoPixelLeds = new ArrayList<>();
        this.buzzer = new Buzzer(Configuration.buzzerPinId);
        this.driveSystem = driveSystem;
        this.timer = new Timer(0);

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
     * This method also sets <a href="{@docRoot}/hardware/NeoPixelLed.html">NeoPixelLeds' setShouldBeOn()</a> to <i>true</i>.
     * @param notification notification that should have its <code>notificationSpecificMethod()</code> executed.
     * @see AbstractNotification#notificationSpecificMethod()
     * @see NeoPixelLed#setShouldBeOn(boolean)
     */
    public void setNotification(AbstractNotification notification) {
        this.notification = notification;

        if (this.notification.getDisableAfterTime() != 0){
            this.timer = new Timer(this.notification.getDisableAfterTime());
            this.timerIsEnabled = true;
        } else {
            this.timerIsEnabled = false;
        }

        for (NeoPixelLed neoPixelLed : this.getNeoPixelLeds()) {
            neoPixelLed.setShouldBeOn(true);
        }
        this.notification.notificationSpecificMethod();
    }

    /**
     * Returns a boolean which is: <p> <ul>
     * <li><i>True</i>: if <code><b>this.</b>notification</code> is an
     * instance of <a href="{@docRoot}/logic/notification/EmptyNotification.html">EmptyNotification</a>.
     * <li><i>False</i>: if <code><b>this.</b>notification</code> is NOT an instance of <code>EmptyNotification</code>.
     * </ul>
     * @return If the last set instance that implements AbstractNotification is an instance of EmptyNotification true, else it returns false.
     */
    private boolean isNotificationEmpty(){
        return this.notification instanceof EmptyNotification;
    }

    /**
     * Runs <code>update()</code> all <b>this.</b>updatables. <p>
     * This method also checks whether the BoeBot is driving (<code>this.drivesystem.getDirection()</code>) backwards,
     * and if it is, it sets (<code>this.setNotification</code>) an
     * <a href="{@docRoot}/logic/notification/EmptyNotification.html">EmptyNotification</a> as the current active notification.
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * @see #setNotification(AbstractNotification)
     * @see Updatable#update()
     */
    @Override
    public void update() {
        for (Updatable u : this.updatables) {
            u.update();
        }

        if (this.timerIsEnabled){
            if (this.timer.timeout()){
                this.setNotification(new EmptyNotification(this.getBuzzer(), this.getNeoPixelLeds()));
            }
        }


        //TODO: Handling the DriveSystem notifications.
//        if (this.driveSystem.getDirection() == DriveSystem.BACKWARD && this.driveSystem.getCurrentSpeed() != 0 && this.isNotificationEmpty()){
//            this.setNotification(new ReverseNotification(this.getBuzzer(), this.getNeoPixelLeds()));
//        } else if (this.driveSystem.getDirection() != DriveSystem.BACKWARD && this.notification instanceof ReverseNotification){
//            this.setNotification(new EmptyNotification(this.getBuzzer(), this.getNeoPixelLeds()));
//        } else if (this.driveSystem.isFollowingRoute() == false){
//            this.setNotification(new EmptyNotification(this.getBuzzer(), this.getNeoPixelLeds()));
//        }
    }
}
