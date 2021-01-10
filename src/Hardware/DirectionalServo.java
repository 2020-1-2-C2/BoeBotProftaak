package Hardware;

/**
 * Subclass of <code>TI.Servo</code>.
 * Stores the orientation of the servo (right or left) so the pulse-width is auto-adjusted to both go forwards or backwards.
 * @see TI.Servo
 * @author Martijn de Kam, Meindert Kempe, Berend de Groot, Lars Hoendervangers
 */
public class DirectionalServo extends TI.Servo {
    private int orientation;

    public final static int LEFTSIDESERVOORIENTATION = -1;
    public final static int RIGHTSIDESERVOORIENTATION = 1;

    /**
     * Constructor initializing the pin and orientation.
     * @param pin         pinId
     * @param orientation 1 is a right servo, -1 is a left servo
     */
    public DirectionalServo(int pin, int orientation) {
        super(pin);
        this.orientation = orientation;
    }

    /**
     * Use the super method to update the servo motor, auto adjust the pulse-width to the orientation of this object.
     * <code>update()</code> method from <code>TI.Servo</code>.
     * @param speed speed in pulse-width.
     * @see TI.Servo#update(int)
     */
    @Override
    public void update(int speed) {
        if (this.orientation == 1) {
            speed = ((speed - 1500) * -1) + 1500;
        }
        super.update(speed);
    }

    /**
     * Gets the pulse-width.
     * @return pulse-width according to the super method, auto adjusting to the orientation of this object.
     */
    @Override
    public int getPulseWidth() {
        if (this.orientation == 1) {
            return ((super.getPulseWidth() - 1500) * -1) + 1500;
        } else {
            return super.getPulseWidth();
        }
    }
}
