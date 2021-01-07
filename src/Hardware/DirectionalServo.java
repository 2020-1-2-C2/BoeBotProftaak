package Hardware;

import Utils.Updatable;

/**
 * Subclass of <code>TI.Servo</code>.
 * Can store the orientation of the servo (right or left) so the pulse-width is auto-adjusted to both go forwards or backwards.
 * @see TI.Servo
 */
public class DirectionalServo extends TI.Servo {
    private int orientation;

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
     * <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * @param speed speed in pulse-width.
     * @see Updatable#update()
     */
    @Override
    public void update(int speed) {
        if (orientation == 1) {
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
        if (orientation == 1) {
            return ((super.getPulseWidth() - 1500) * -1) + 1500;
        } else {
            return super.getPulseWidth();
        }
    }
}
