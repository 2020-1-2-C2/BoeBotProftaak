package Hardware;

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
        this(5);
    }

    public void emergencyBrake() {
        this.servoLeft.update(1500);
        this.servoRight.update(1500);
    }

    /**
     *
     * @param speed speed witch the boebot wil drive at
     */
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

    /**
     *
     * @param speed speed the boebot wants to drive at
     * @param servo servo witch speed wil be changed
     * @return boolean if the current speed is the wanted speed
     */
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

    /**
     *
     * @param speed speed the boebot wants to drive at
     * @param acceleration time taken between every increase of speed
     */
    public void goToSpeed(int speed, int acceleration) {
        if (speed > 1300 && speed < 1700) {
            boolean leftIsSpeed = goToSpeedOneStep(speed, this.servoLeft);
            boolean rightIsSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoRight);
            while (!(leftIsSpeed && rightIsSpeed)) {
                leftIsSpeed = goToSpeedOneStep(speed, this.servoLeft);
                rightIsSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoRight);
                BoeBot.wait(acceleration);
            }
        } else {
            System.out.println("inpossible speed");
        }
    }

    /**
     *
     * @param speed speed the boebot wants to drive at
     * @param acceleration time taken between every increase of speed
     */
    public void goToSpeedLeft (int speed, int acceleration) {
        if (speed > 1300 && speed < 1700) {
            boolean isSpeed = goToSpeedOneStep(speed, this.servoLeft);
            while (!isSpeed) {
                isSpeed = goToSpeedOneStep(speed, this.servoLeft);
                BoeBot.wait(acceleration);
            }
        } else {
            System.out.println("inpossible speed");
        }
    }

    /**
     *
     * @param speed speed the boebot wants to drive at
     * @param acceleration time taken between every increase of speed
     */
    public void goToSpeedRight (int speed, int acceleration) {
        if (speed > 1300 && speed < 1700) {
            boolean isSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoRight);
            while (!isSpeed) {
                isSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoRight);
                BoeBot.wait(acceleration);
            }
        } else {
            System.out.println("inpossible speed");
        }
    }

    public int getStepSize() {
        return this.stepSize;
    }

    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }
}
