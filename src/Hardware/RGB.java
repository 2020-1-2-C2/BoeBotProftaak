package Hardware;

import TI.BoeBot;
import TI.PWM;
import TI.Timer;
import Utils.Led;

public class RGB implements Led {
    private int red;
    private int green;
    private int blue;
    private int pinRed;
    private int pinGreen;
    private int pinBlue;
    private boolean isOn;
    private Timer blinkingTimer;
    private int interval;

    public RGB(int red, int green, int blue, int pinRed, int pinGreen, int pinBlue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.pinRed = pinRed;
        this.pinGreen = pinGreen;
        this.pinBlue = pinBlue;
    }

    /**
     * Mixes the colors
     * @param red boolean to turn the color on or off.
     * @param green boolean to turn the color on or off.
     * @param blue boolean to turn the color on or off.
     */
    //TODO convert to PWM
    public void setColor(boolean red, boolean green, boolean blue) {
        BoeBot.digitalWrite(this.pinRed, red);
        BoeBot.digitalWrite(this.pinGreen, green);
        BoeBot.digitalWrite(this.pinBlue, blue);
    }

    @Override
    public void on() {
        this.isOn = true;
        setColor(true, true, true);
    }

    @Override
    public void off() {
        this.isOn = false;
        setColor(false, false, false);
    }

    @Override
    public boolean getIsOn() {
        return this.isOn;
    }

    @Override
    public void fade(int fade) {
        PWM pwmRed = new PWM(this.pinRed, fade);
        PWM pwmGreen = new PWM(this.pinGreen, fade);
        PWM pwmBlue = new PWM(this.pinBlue, fade);
        pwmRed.update(fade);
        pwmGreen.update(fade);
        pwmBlue.update(fade);
    }

    @Override
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

    public int getBlue() {
        return this.blue;
    }

    public int getGreen() {
        return this.green;
    }

    public int getRed() {
        return this.red;
    }

    @Override
    public void update() {
        if (this.blinkingTimer.timeout()) {
            blink(this.interval);
            this.blinkingTimer.mark();
        }
    }
}
