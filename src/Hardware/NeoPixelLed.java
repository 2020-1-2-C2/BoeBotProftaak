package Hardware;

import TI.BoeBot;
import TI.Timer;
import Utils.Led;

import java.awt.*;

public class NeoPixelLed implements Led {
    private int id;
    private Timer blinkingTimer;
    private int interval;
    private boolean timerIsEnabled = false;
    private boolean isOn;
    private Color color;

    public NeoPixelLed(int id) {
        this.id = id;
        this.blinkingTimer = new Timer(1000); //TODO: Check why we use a timer like this & Check whether a constructor with a customizable timer would be beneficial
    }

    @Override
    public void on() {
        BoeBot.setStatusLed(true);
        this.isOn = true;
    }

    @Override
    public void off() {
        BoeBot.setStatusLed(false);
        this.isOn = false;
    }

    public void setColor(Color color) {
        this.color = color;
        if (color.getRed() == 0 || color.getGreen() == 0 || color.getBlue() == 0) {
            off();
        } else {
            BoeBot.rgbSet(id, color);
        }
    }

    @Override
    public void fade(int fade) {
        BoeBot.rgbSet(id, fade, fade, fade);
    }

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

    //TODO: Remove getBlue(), getGreen() & getRed().
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
        if (this.blinkingTimer.timeout() && this.timerIsEnabled) {
            blink(this.interval);
            this.blinkingTimer.mark();
        }
    }
}
