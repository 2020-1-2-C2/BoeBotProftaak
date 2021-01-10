package Utils;

/**
 * Subclass of TI.Timer.
 * Adds a private boolean isOn to keep track of whether the timer is on or not internally.
 * @see TI.Timer
 * @author Martijn de Kam, Meindert Kempe, Berend de Groot
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
     * Uses the super constructor and set isOn to be true.
     *
     * @param interval Timer interval.
     * @param isOn Sets the value of isOn.
     * @see TI.Timer#Timer(int)
     */
    public TimerWithState(int interval, boolean isOn) {
        super(interval);
        this.isOn = isOn;
    }

    /**
     * Auto-generated getter.
     *
     * @return Boolean; the value of isOn.
     */
    public boolean isOn() {
        return this.isOn;
    }

    /**
     * Auto-generated setter.
     *
     * @param on Sets the value of isOn.
     */
    public void setOn(boolean on) {
        this.isOn = on;
    }
}
