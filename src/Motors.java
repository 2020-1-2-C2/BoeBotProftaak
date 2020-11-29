import TI.BoeBot;
import TI.Servo;
import TI.Timer;

public class Motors {
    private Servo servoLeft;
    private Servo servoRight;
    private int speed;

    public Motors(int speed) {
        this.servoLeft = new Servo(12);
        this.servoRight = new Servo(13);
        this.speed = speed;
    }

    public void emergencyBrake() {
        this.servoLeft.update(1500);
        this.servoRight.update(1500);
    }

    public void drive() {
        this.servoLeft.update(this.speed);
        this.servoRight.update(((this.speed - 1500) * -1) + 1500);
    }

    public void goToSpeed(int speed, int acceleration) {
        int goToSpeed = speed;
        Timer timer = new Timer(acceleration);

        while (this.servoLeft.getPulseWidth() != speed) {

            if (timer.timeout()) {
                if (this.servoLeft.getPulseWidth() < speed) {
                    setSpeed(speed++);
                    drive();
                } else if (this.servoLeft.getPulseWidth() > speed) {
                    setSpeed(speed++);
                    drive();
                }

                System.out.println("Left: " + this.servoLeft.getPulseWidth());
                System.out.println("Right: " + this.servoRight.getPulseWidth());
                timer.mark();
            }
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
