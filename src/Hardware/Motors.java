package Hardware;

import TI.BoeBot;
import TI.Servo;
import TI.Timer;
import Utils.Updatable;

public class Motors implements Updatable {
    private Servo servoLeft;
    private Servo servoRight;
    private int stepSize;
    private Timer servoTimer;
    private boolean acceleratingLeft;
    private boolean acceleratingRight;
    private int speedLeft;
    private int speedRight;
    private int acceleration;

    public Motors(int stepSize) {
        this.servoLeft = new Servo(12);
        this.servoRight = new Servo(13);
        this.stepSize = stepSize;
        this.servoTimer = new Timer(0);
        this.acceleratingLeft = false;
        this.acceleratingRight = false;
        this.speedLeft = 1500;
        this.speedRight = 1500;
        this.acceleration = 10;
    }

    public Motors() {
        this(5);
    }

    public void emergencyBrake() {
        drive(1500);
    }

    /**
     * Goes to the desired speed instantly.
     * @param speed speed witch the boebot wil drive at
     */
    public void drive(int speed) {
        this.servoLeft.update(speed);
        this.servoRight.update(((speed - 1500) * -1) + 1500);
        this.speedLeft = speed;
        this.speedRight = ((speed - 1500) * -1) + 1500;
    }


    public void driveLeft(int speed) {
        this.servoLeft.update(speed);
        this.speedLeft = speed;
    }

    public void driveRight(int speed) {
        this.servoRight.update(((speed - 1500) * -1) + 1500);
        this.speedRight = ((speed - 1500) * -1) + 1500;
    }

    /**
     * Changes the speed one step closer to the wanted speed.
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
     * Repeats the goToSpeedOneStep until the speed is correct.
     * @param speed speed the boebot wants to drive at
     * @param acceleration time taken between every increase of speed
     */
    public void goToSpeed(int speed, int acceleration) {
        if (speed >= 1300 && speed <= 1700) {
            boolean leftIsSpeed = goToSpeedOneStep(speed, this.servoLeft);
            boolean rightIsSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoRight);
            if (!(leftIsSpeed && rightIsSpeed)) {
                leftIsSpeed = goToSpeedOneStep(speed, this.servoLeft);
                rightIsSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoRight);
                this.servoTimer.setInterval(acceleration);
                this.speedLeft = speed;
                this.speedRight = speed;
                this.acceleratingLeft = true;
                this.acceleratingRight = true;
                this.acceleration = acceleration;
            }
        } else {
            this.acceleratingLeft = false;
            this.acceleratingRight = false;
            System.out.println("impossible speed");
        }
    }

    /**
     * The same as goToSpeed, but only for the left servo.
     * @param speed speed the boebot wants to drive at
     * @param acceleration time taken between every increase of speed
     */
    public void goToSpeedLeft (int speed, int acceleration) {
        if (speed >= 1300 && speed <= 1700) {
            boolean isSpeed = goToSpeedOneStep(speed, this.servoLeft);
            if (!isSpeed) {
                isSpeed = goToSpeedOneStep(speed, this.servoLeft);
                this.servoTimer.setInterval(acceleration);
                this.speedLeft = speed;
                this.acceleratingLeft = true;
                this.acceleration = acceleration;
            }
        } else {
            this.acceleratingLeft = false;
            System.out.println("impossible speed");
        }
    }

    /**
     * The same as goToSpeed, but only for the right servo.
     * @param speed speed the boebot wants to drive at
     * @param acceleration time taken between every increase of speed
     */
    public void goToSpeedRight (int speed, int acceleration) {
        if (speed >= 1300 && speed <= 1700) {
            boolean isSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoRight);
            if (!isSpeed) {
                isSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoRight);
                this.servoTimer.setInterval(acceleration);
                this.speedRight = speed;
                this.acceleratingRight = true;
                this.acceleration = acceleration;
            }
        } else {
            this.acceleratingRight = false;
            System.out.println("inpossible speed");
        }
    }

    public int getStepSize() {
        return this.stepSize;
    }

    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    @Override
    public void Update() {
        if (this.servoTimer.timeout()) {
            if (this.acceleratingLeft && this.acceleratingRight) {
                goToSpeed(this.speedLeft, this.acceleration);
            } else if (this.acceleratingLeft) {
                goToSpeedLeft(this.speedLeft, this.acceleration);
            } else if (this.acceleratingRight) {
                goToSpeedRight(this.speedRight, this.acceleration);
            }
            this.servoTimer.mark();
        }
    }
}