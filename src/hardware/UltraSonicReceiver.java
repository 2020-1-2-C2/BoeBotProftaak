package hardware;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import utils.UltraSonicCallback;
import utils.Updatable;

/**
 * Class for the hardware part ultrasonic-sensor, contains methods to receive and interpret the signal from the ultrasonic-sensor.
 * @version 1.0
 * @see Updatable
 * @see UltraSonicCallback
 * @author Martijn de Kam, Berend de Groot
 */
public class UltraSonicReceiver implements Updatable {

    private int pinIdTrigger;
    private int pinIdEcho;
    private Timer ultraSonicPulseTimer = new Timer(65);
    private UltraSonicCallback ultraSonicCallback;

    /**
     * Constructor for the ultrasonic-sensor.
     * @param pinIdTrigger       ultrasonic-sensor trigger pin id.
     * @param pinIdEcho          ultrasonic-sensor echo pin id.
     * @param ultraSonicCallback ultraSonicCallback object.
     */
    public UltraSonicReceiver(int pinIdTrigger, int pinIdEcho, UltraSonicCallback ultraSonicCallback) {
        this.pinIdTrigger = pinIdTrigger;
        BoeBot.setMode(this.pinIdTrigger, PinMode.Output);
        this.pinIdEcho = pinIdEcho;
        BoeBot.setMode(this.pinIdEcho, PinMode.Input);
        this.ultraSonicCallback = ultraSonicCallback;
        this.ultraSonicPulseTimer.mark();
    }

    /**
     * Send an ultrasonic pulse and measure the time it takes to receive the pulse back in microseconds.
     * If the pulse length is less than 0 a null object is returned.
     * @return time in microseconds to send and receive back an ultrasonic pulse.
     */
    private Integer ultraSonicPulse() {
        BoeBot.digitalWrite(this.pinIdTrigger, true);
        BoeBot.wait(1);
        BoeBot.digitalWrite(this.pinIdTrigger, false);

        //Pulse length in microseconds.
        int pulse = BoeBot.pulseIn(this.pinIdEcho, true, 10000);

        if (pulse < 0) {
            return null;
        } else {
            return pulse;
        }
    }

    /**
     * Using the pulse length in microseconds, calculate the distance of an ultrasonic-pulse echo in cm.
     * The pulse length can be a null object, in which case a null object is directly returned
     * @return The distance an ultrasonic-pulse has traveled one way, in cm.
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
     * If enough time has elapsed for the timer, then an ultrasonic pulse is sent, of which the distance is
     * calculated and then sent to the callback object.
     * @see Updatable#update()
     */
    @Override
    public void update() {
        if (this.ultraSonicPulseTimer.timeout()) {
            this.ultraSonicPulseTimer.mark();
            this.ultraSonicCallback.onUltraSonicPulse(distance());
        }
    }
}