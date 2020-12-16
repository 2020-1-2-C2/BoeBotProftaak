package Utils;

/**
 * Subclass of TI.Timer
 * Adds a private boolean isOn to keep track of whether the timer is on or not internally
 */
public class TimerWithState extends TI.Timer {

    private boolean isOn;

    public TimerWithState(int i) {
        super(i);
        this.isOn = true;
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
