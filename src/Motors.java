import TI.BoeBot;
import TI.Servo;

public class Motors {
    private Servo servoLeft;
    private Servo servoRight;

    public Motors() {
        this.servoLeft = new Servo(12);
        this.servoRight = new Servo(13);
    }

    public void emergencyBrake() {
        this.servoLeft.update(1500);
        this.servoRight.update(1500);
    }

    public void drive(int speed) {
        this.servoLeft.update(speed);
        this.servoRight.update(((speed - 1500) * -1) + 1500);
    }

    public void driveLeft(int speed) {
        this.servoLeft.update(speed);
    }

    public void driveRight(int speed) {
        this.servoRight.update(((speed - 1500) * -1) + 1500);
    }

    private boolean goToSpeedOneStep(int speed,Servo servo) {
        int stepSize = 10;
        int currentSpeed = servo.getPulseWidth();
        if (currentSpeed > speed - stepSize && currentSpeed < speed + stepSize){
            servo.update(speed);
            return true;
        } else if (currentSpeed < speed) {
            currentSpeed += stepSize;
            servo.update(currentSpeed);
            System.out.println(servo.getPulseWidth());
            return false;
        } else if (currentSpeed > speed) {
            currentSpeed -= stepSize;
            servo.update(currentSpeed);
            System.out.println(servo.getPulseWidth());
            return false;
        }
        return true;
    }

    public void goToSpeed(int speed, int acceleration) {
        boolean leftIsSpeed = goToSpeedOneStep(speed, this.servoLeft);
        boolean rightIsSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoRight);
        while (!(leftIsSpeed && rightIsSpeed)) {
            leftIsSpeed = goToSpeedOneStep(speed, this.servoLeft);
            rightIsSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoRight);
            BoeBot.wait(acceleration);
        }
    }

    public void goToSpeedLeft (int speed, int acceleration) {
        boolean isSpeed = goToSpeedOneStep(speed, this.servoLeft);
        while (!isSpeed) {
            isSpeed = goToSpeedOneStep(speed, this.servoLeft);
            BoeBot.wait(acceleration);
        }
    }

    public void goToSpeedRight (int speed, int acceleration) {
        boolean isSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoRight);
        while (!isSpeed) {
            isSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoRight);
            BoeBot.wait(acceleration);
        }
    }



    /* public void goToSpeed(int speed, int acceleration) {
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
    }*/
}
