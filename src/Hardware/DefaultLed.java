package Hardware;

import TI.BoeBot;
import TI.PWM;
import TI.Timer;
import Utils.Led;
import Utils.Updatable;

/**
 * DefaultLed class, which should only have one instance. The methods are used to process received commands from the PC using the GUI.
 * @see Utils.Updatable
 */
public class DefaultLed implements Led {
    private int pinId;
    private boolean isOn;
    private Timer blinkingTimer;
    private int interval;
    private boolean timerIsEnabled = false;

    /**
     * Constructor for Hardware.DefaultLed class.
     * @param pinId the pin where the LED is attached.
     */
    public DefaultLed(int pinId) {
        this.pinId = pinId;
        this.blinkingTimer = new Timer(1000);
    }

    @Override
    public void on() {
        this.isOn = true;
        BoeBot.digitalWrite(this.pinId, false);
    }

    @Override
    public void off() {
        this.isOn = false;
        BoeBot.digitalWrite(this.pinId, true);
    }

    /**
     * Fade the LED.
     * @param fade fades the LED. (Value: 0-100, 0 is full brightness, 100 is off.
     * @see PWM
     */
    @Override
    public void fade(int fade) {
        PWM pwm = new PWM(this.pinId, fade);
        pwm.update(fade);
    }

    /**
     * Blink the LED
     * @param interval is the interval between each blink.
     */
    @Override
    public void blink(int interval) {
        this.interval = interval;
        if (interval > 0) {
            this.timerIsEnabled = true;
            this.blinkingTimer.setInterval(interval);
        } else {
            this.timerIsEnabled = false;
        }
        if (getIsOn()) {
            off();
        } else {
            on();
        }
    }

    @Override
    public boolean getIsOn() {
        return this.isOn;
    }

    /**
     * Updates the blinker.
     * <code>update()</code> from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * @see Updatable#update()
     */
    @Override
    public void update() {
        if (this.blinkingTimer.timeout() && this.timerIsEnabled) {
            blink(this.interval);
            this.blinkingTimer.mark();
        }
    }
}
