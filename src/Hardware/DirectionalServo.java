package Hardware;

public class DirectionalServo extends TI.Servo {
    private int orientation;

    /**
     *
     * @param pin
     * @param orientation 1 is a right servo, -1 is a left servo
     */
    public DirectionalServo(int pin, int orientation) {
        super(pin);
        this.orientation = orientation;
    }

    @Override
    public void update(int speed) {
        if (orientation == 1) {
            speed = ((speed - 1500) * -1) + 1500;
        }
        super.update(speed);
    }

    @Override
    public int getPulseWidth() {
        if (orientation == 1) {
            return ((super.getPulseWidth() - 1500) * -1) + 1500;
        } else {
            return super.getPulseWidth();
        }
    }
}
