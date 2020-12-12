// TODO, no support for single pin ultrasonicsensor, if used, need to implement new methods/subclasses.
package Hardware;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import Utils.UltraSonicCallback;
import Utils.Updatable;

/**
 * Class for the hardware part ultrasonicsensor, contains methods to receive and interprit the signal from the ultrasonicsensor.
 */
public class UltraSonicReceiver implements Updatable {

    private int pinIdTrigger;
    private int pinIdEcho;
    private Timer ultraSonicPulseTimer = new Timer(50);
    private UltraSonicCallback ultraSonicCallback;

    /**
     * Constructor for the ultrasonicsensor.
     * @param pinIdTrigger ultrasonicsensor trigger pin id.
     * @param pinIdEcho ultrasonicsensor echo pin id.
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
     * Measure the amount of time it takes to send and receive an ultrasonic pulse in microseconds.
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
     * Calculate the distance of an ultrasonicpulse echo in cm.
     * @return the distance an ultrasonicpulse has traveled one way, in cm.
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
     * If enough time has elapsed for the timer, then an ultrasonic pulse is sent, of which the distance is calculated and then sent to the callback object.
     */
    @Override
    public void update() {
//        System.out.println("Ultrasound timeout check");
        if (this.ultraSonicPulseTimer.timeout()) {
//            System.out.println("Ultrasound pulse");
            this.ultraSonicCallback.onUltraSonicPulse(distance());
        }
    }
}
