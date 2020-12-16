package Hardware;

/**
 * Subclass of TI.Servo
 * Can store the orientation of the servo (right or left) so the pulsewidth is autoadjusted to both go forwards or backwards
 */
public class DirectionalServo extends TI.Servo {
    private int orientation;

    /**
     * @param pin pinId
     * @param orientation 1 is a right servo, -1 is a left servo
     */
    public DirectionalServo(int pin, int orientation) {
        super(pin);
        this.orientation = orientation;
    }

    /**
     * Use the super method to update the servo motor, auto adjust the pulsewidth to the orientation of this object
     *
     * @param speed speed in pulsewidth
     */
    @Override
    public void update(int speed) {
        if (orientation == 1) {
            speed = ((speed - 1500) * -1) + 1500;
        }
        super.update(speed);
    }

    /**
     * @return pulsewidth according to the super method, auto adjusting to the orientation of this object
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
