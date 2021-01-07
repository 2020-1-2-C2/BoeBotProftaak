package Hardware;

import TI.BoeBot;
import TI.Timer;
import Utils.Led;

import java.awt.*;

/**
 * Class used by the NeoPixelLed hardware component. The NeoPixelLeds are used by the notificationssystem to signal the current state the BoeBot is in.
 * @see Utils.Updatable
 * @see Utils.Led
 * Class is used by the notificationssystem.
 * @see Logic.Notification.AbstractNotification
 */
public class NeoPixelLed implements Led {
    private int id;
    private Timer blinkingTimer;
    private int interval;
    private boolean timerIsEnabled = false;
    private boolean isOn;
    private Color color;
    private boolean shouldBeOn = false;

    /**
     * Constructor for the NeoPixelLed.
     * @param id Takes a pinId so that we now which LED is which.
     */
    public NeoPixelLed(int id) {
        this.id = id;
        this.blinkingTimer = new Timer(0); //TODO: Check value.
        this.color = Color.white;
    }

    /**
     * Overrides the on() function in Led.java.
     * Is used to turn the Led on in its default state.
     * @see Led#on()
     */
    @Override
    public void on() {
        BoeBot.setStatusLed(true);
        BoeBot.rgbSet(this.id, this.color);
        BoeBot.rgbShow();
        this.isOn = true;
    }

    /**
     * Overrides the off() function in Led.java.
     * Is used to turn the Led off.
     * @see Led#off()
     */
    @Override
    public void off() {
        BoeBot.setStatusLed(false);
        BoeBot.rgbSet(this.id, Color.black);
        BoeBot.rgbShow(); //Nani?
        this.isOn = false;
    }

    /**
     * Changes to the color to the color specified in the parameter.
     * @param color Sets the color of this specific NeoPixelLed to the parameter.
     */
    public void setColor(Color color) {
        this.color = color;
        BoeBot.rgbSet(id, color);
    }

    /**
     * Overrides the fade() method in Led.java.
     * @param fade Int used to specify the color.
     * @see Led#fade(int)
     */
    @Override
    public void fade(int fade) {
        BoeBot.rgbSet(id, fade, fade, fade);
    }

    /**
     * Overrides the blink() method in Led.java.
     * Results into this NeoPixelLed instance being turned on and off after a timer (with the specified interval) times out.
     * @param interval Int specifying the interval of the timer used by this method.
     * @see Led#blink(int)
     */
    @Override
    public void blink(int interval) {
        System.out.println("NeoPixelLed (" + this.id + ") is blinking at interval " + interval + ".");
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

    /**
     * Overrides the getIsOn() method in Led.java.
     * A boolean used to check whether the light is on.
     * @return The boolean this.isOn.
     */
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

    /**
     * Update method.
     * @see Utils.Updatable
     */
    @Override
    public void update() {
        if (this.shouldBeOn){
            if (this.blinkingTimer.timeout()) {
                blink(this.interval);
                this.blinkingTimer.mark();
            }
        } else { //TODO: Check whether this is necessary.
            this.shouldBeOn = false;
            this.off();
        }
    }

    /**
     * Auto-generated setter.
     * Method used to change the interval of the timer to the value of the parameter.
     * @param amountOfTime Int specifying the amount of time the timer should be.
     */
    public void setBlinkingTimer(int amountOfTime) {
        this.blinkingTimer.setInterval(amountOfTime);
    }

    /**
     * Method used by the notificationssystem to check whether the light should be on or off.
     * @param shouldBeOn Boolean specifying whether the Led should be on or off.
     */
    public void setShouldBeOn(boolean shouldBeOn) {
        this.shouldBeOn = shouldBeOn;
    }
}
