package Hardware;

import TI.BoeBot;
import Utils.Updatable;

public class Buzzer implements Updatable {
    private int pinId;
    private boolean isOn;

    /**
     *
     * @param pinId is the pin where the buzzer is connected to.
     */
    public Buzzer(int pinId) {
        this.pinId = pinId;
    }

    /**
     * Buzz for 1 second on 1000Hz.
     */
    public void buzz() {
        buzz(1000,1000);
    }

    /**
     * Buzz for a certain amount of time on 1000Hz.
     * @param time in milliseconds.
     */
    public void buzz(int time) {
        buzz(time,1000);
    }

    /**
     * Buzz for a certain amount of time on a certain frequency.
     * @param time in milliseconds.
     * @param freq in Hz.
     */
    public void buzz(int time, int freq) {
        BoeBot.freqOut(this.pinId, freq, time);
    }

    /**
     * Buzz for half a second.
     */
    public void beep() {
        buzz(500);
    }

    /**
     * Turns the buzzer off. (If the buzzer won't shut off automatically.)
     */
    public void off() {
        this.isOn = false;
    }

    /**
     * @return the isOn boolean.
     */
    public boolean getIsOn() {
        return this.isOn;
    }

    @Override
    public void update() {

    }
}
