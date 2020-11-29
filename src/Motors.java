import TI.BoeBot;
import TI.Servo;

public class Motors {
    private Servo servoLeft;
    private Servo servoRight;
    private int stepSize;

    public Motors(int stepSize) {
        this.servoLeft = new Servo(12);
        this.servoRight = new Servo(13);
        this.stepSize = stepSize;
    }

    public Motors() {
        this(10);
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
        int currentSpeed = servo.getPulseWidth();
        if (currentSpeed > speed - this.stepSize && currentSpeed < speed + this.stepSize){
            servo.update(speed);
            return true;
        } else if (currentSpeed < speed) {
            currentSpeed += this.stepSize;
            servo.update(currentSpeed);
            System.out.println(servo.getPulseWidth());
            return false;
        } else if (currentSpeed > speed) {
            currentSpeed -= this.stepSize;
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

    public int getStepSize() {
        return this.stepSize;
    }

    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }
}
