package Utils;

/**
 * Subclass of TI.Timer
 * Adds a private boolean isOn to keep track of whether the timer is on or not internally
 */
public class TimerWithState extends TI.Timer {

    private boolean isOn;

    /**
     * Use the super constructor and set isOn to be true
     *
     * @param interval timer interval
     */
    public TimerWithState(int interval) {
        this(interval, true);
    }

    /**
     * Use the super constructor and set isOn to be true
     *
     * @param interval timer interval
     * @param isOn set the value of isOn
     */
    public TimerWithState(int interval, boolean isOn) {
        super(interval);
        this.isOn = isOn;
    }

    /**
     * Standard getter
     *
     * @return the value of isON
     */
    public boolean isOn() {
        return this.isOn;
    }

    /**
     * Standard setter
     *
     * @param on set the value of isOn
     */
    public void setOn(boolean on) {
        isOn = on;
    }
}
