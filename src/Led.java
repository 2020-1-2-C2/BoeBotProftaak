import TI.BoeBot;
import TI.PWM;
import TI.Timer;

public class Led implements Updatable {
    private int pinId;
    private boolean isOn;
    private Timer blinkingTimer;
    private int interval;

    /**
     * Constructor for Led class
     * @param pinId the pin where the LED is attached.
     */
    public Led(int pinId) {
        this.pinId = pinId;
    }

    public void on() {
        this.isOn = true;
        BoeBot.digitalWrite(this.pinId, false);
    }

    public void off() {
        this.isOn = false;
        BoeBot.digitalWrite(this.pinId, true);
    }

    /**
     * Fade the LED
     * @param fade fades the LED. (Value: 0-255, 0 is full brightness, 255 is off.
     */
    public void fade(int fade) {
        PWM pwm = new PWM(this.pinId, fade);
        pwm.update(fade);
    }

    /**
     * Blink the LED
     * @param interval is the interval between each blink.
     */
    public void blink(int interval) {
        this.interval = interval;
        if (interval > 0) {
            this.blinkingTimer = new Timer(interval);
        }
        if (getIsOn()) {
            off();
        } else {
            on();
        }
    }

    public boolean getIsOn() {
        return this.isOn;
    }

    /**
     * Updates the blinker
     */
    @Override
    public void Update() {
        if (this.blinkingTimer.timeout()) {
            blink(this.interval);
            this.blinkingTimer.mark();
        }
    }
}
