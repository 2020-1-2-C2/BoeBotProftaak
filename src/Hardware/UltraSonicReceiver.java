package Hardware;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import Utils.UltraSonicCallback;
import Utils.Updatable;

/**
 * Class for the hardware part ultrasonicsensor, contains methods to receive and interprit the signal from the ultrasonicsensor.
 * @version 1.0
 * @see Updatable
 * @see UltraSonicCallback
 */
public class UltraSonicReceiver implements Updatable {

    private int pinIdTrigger;
    private int pinIdEcho;
    private Timer ultraSonicPulseTimer = new Timer(50);
    private UltraSonicCallback ultraSonicCallback;

    /**
     * Constructor for the ultrasonicsensor.
     *
     * @param pinIdTrigger       ultrasonicsensor trigger pin id.
     * @param pinIdEcho          ultrasonicsensor echo pin id.
     * @param ultraSonicCallback ultrasoniccallback object.
     */
    public UltraSonicReceiver(int pinIdTrigger, int pinIdEcho, UltraSonicCallback ultraSonicCallback) {
        this.pinIdTrigger = pinIdTrigger;
        BoeBot.setMode(this.pinIdTrigger, PinMode.Output);
        this.pinIdEcho = pinIdEcho;
        BoeBot.setMode(this.pinIdEcho, PinMode.Input);
        this.ultraSonicCallback = ultraSonicCallback;
    }

    /**
     * Send an ultrasonic pulse and measure the time it takes to receive the pulse back in microseconds.
     * If the pulse length is less than 0 a null object is returned.
     *
     * @return time in microseconds to send and receive back an ultrasonic pulse.
     */
    private Integer ultraSonicPulse() {
        BoeBot.digitalWrite(this.pinIdTrigger, true);
        BoeBot.wait(1);
        BoeBot.digitalWrite(this.pinIdTrigger, false);

        // pulse lengte in microseconden
        int pulse = BoeBot.pulseIn(this.pinIdEcho, true, 10000);

        if (pulse < 0) {
            return null;
        } else {
            return pulse;
        }
    }

    /**
     * Using the pulse length in microseconds, calculate the distance of an ultrasonicpulse echo in cm.
     * The pulse length can be a null object, in which case a null object is directly returned
     *
     * @return The distance an ultrasonicpulse has traveled one way, in cm.
     */
    private Integer distance() {
        Integer pulse = ultraSonicPulse();
        if (pulse == null) {
            return null;
        } else {
            return pulse / 58;
        }
    }

    /**
     * Overrides the <code>update()</code> method.
     * If enough time has elapsed for the timer, then an ultrasonic pulse is sent, of which the distance is calculated and then sent to the callback object.
     * @see Updatable#update()
     */
    @Override
    public void update() {
        if (this.ultraSonicPulseTimer.timeout()) {
            this.ultraSonicCallback.onUltraSonicPulse(distance());
        }
    }
}
