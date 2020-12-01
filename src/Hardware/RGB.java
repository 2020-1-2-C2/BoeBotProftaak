package Hardware;

import TI.BoeBot;
import TI.PWM;
import TI.Timer;
import Utils.Led;

import java.awt.*;

public class RGB implements Led {
    private int pinRed;
    private int pinGreen;
    private int pinBlue;
    private boolean isOn;
    private Timer blinkingTimer;
    private int interval;
    private PWM pwmRed;
    private PWM pwmGreen;
    private PWM pwmBlue;
    private Color color;

    public RGB(int pinRed, int pinGreen, int pinBlue, Color color) {
        this.pinRed = pinRed;
        this.pinGreen = pinGreen;
        this.pinBlue = pinBlue;
        this.color = color;
        pwmRed = new PWM(this.pinRed, color.getRed());
        pwmGreen = new PWM(this.pinGreen, color.getGreen());
        pwmBlue = new PWM(this.pinBlue, color.getBlue());
    }

    /**
     * Mixes the colors
     * @param color
     */
    //TODO convert to PWM
    public void setColor(Color color) {
        this.color = color;
        pwmRed.update(color.getRed());
        pwmGreen.update(color.getGreen());
        pwmBlue.update(color.getBlue());
    }

    @Override
    public void on() {
        this.isOn = true;
        setColor(Color.white);
    }

    @Override
    public void off() {
        this.isOn = false;
        setColor(Color.white);
    }

    @Override
    public boolean getIsOn() {
        return this.isOn;
    }

    @Override
    public void fade(int fade) {
        this.color = new Color(fade, fade, fade);
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
        return this.color.getBlue();
    }

    public int getGreen() {
        return this.color.getGreen();
    }

    public int getRed() {
        return this.color.getRed();
    }

    @Override
    public void update() {
        if (this.blinkingTimer.timeout()) {
            blink(this.interval);
            this.blinkingTimer.mark();
        }
    }
}
