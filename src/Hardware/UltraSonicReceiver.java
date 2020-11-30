package Hardware;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import Utils.UltraSonicCallback;
import Utils.Updatable;

/**
 * Klasse voor het hardware onderdeel ultrasoonsensor, bevat methodes om het signaal van de ultrasoonsensor te ontvangen en verwerken.
 */
public class UltraSonicReceiver implements Updatable {

    private int pinIdTrigger;
    private int pinIdEcho;
    private Timer ultraSonicPulseTimer = new Timer(50);
    private UltraSonicCallback ultraSonicCallback;
    private boolean isDetected;

    /**
     * Constructor voor de ultrasonic ontvanger
     * @param pinIdTrigger
     * @param pinIdEcho
     * @param ultraSonicCallback
     */
    public UltraSonicReceiver(int pinIdTrigger, int pinIdEcho, UltraSonicCallback ultraSonicCallback) {
        this.pinIdTrigger = pinIdTrigger;
        BoeBot.setMode(this.pinIdTrigger, PinMode.Output);
        this.pinIdEcho = pinIdEcho;
        BoeBot.setMode(this.pinIdEcho, PinMode.Input);
        this.ultraSonicCallback = ultraSonicCallback;
    }

    /**
     * Meet de afstand in microseconden.
     * @return
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
     * Berekend de gemeten afstand in cm.
     * @return
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
     * Als er genoeg tijd is verstreken voor de timer, dan wordt een ultrasonische puls uitgestuurd, daarvan de afstand berekend en teruggeven naar de callback.
     */
    @Override
    public void update() {
        if (this.ultraSonicPulseTimer.timeout()) {
            this.ultraSonicCallback.onUltraSonicPulse(distance());
        }
    }
}
