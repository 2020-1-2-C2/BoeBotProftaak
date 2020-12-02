package Hardware;

import TI.Servo;
import TI.Timer;
import Utils.Motor;

//TODO make interface "Motors" and refactor this to "Servos" implementing "Motors"
public class ServoMotor implements Motor {
    private Servo servoLeft;
    private Servo servoRight;
    private Timer timerLeft;
    private Timer timerRight;
    private boolean timerIsEnabledLeft;
    private boolean timerIsEnabledRight;

    private int wantedSpeedLeft;
    private final int STEP_SIZE_LEFT = 2;
    private int wantedSpeedRight;
    private final int STEP_SIZE_RIGHT = 2;


    private final int MAX_FORWARD_SPEED = 1700;
    private final int MAX_BACKWARD_SPEED = 1300;
    private final int STANDSTILL_SPEED = 1500;

    public ServoMotor(Servo servoLeft, Servo servoRight) {
        this.servoLeft = servoLeft;
        this.servoRight = servoRight;
        this.timerLeft = new Timer(1000);
        this.timerRight = new Timer(1000);
        timerIsEnabledLeft = false;
        timerIsEnabledRight = false;
    }

    public ServoMotor() {
        this(new Servo(12), new Servo(13));
    }

    private int reverseValue(int value) {
        return ((value - STANDSTILL_SPEED) * -1) + STANDSTILL_SPEED;
    }

    private int percentToValue(int percent) {
        //convert percentile to driving value
        if (percent > 0) {
            int diff = MAX_FORWARD_SPEED - STANDSTILL_SPEED;
            return ((diff / 100) * percent) + STANDSTILL_SPEED;
        } else {
            int diff = MAX_BACKWARD_SPEED - STANDSTILL_SPEED;
            return ((diff / 100) * percent) + STANDSTILL_SPEED;
        }
    }

    private int valueToPercent(int value) {
        if (value > STANDSTILL_SPEED) {
            int diff = MAX_FORWARD_SPEED - STANDSTILL_SPEED;
            return ((value - STANDSTILL_SPEED) * 100) / diff;
        } else {
            int diff = MAX_BACKWARD_SPEED - STANDSTILL_SPEED;
            return ((STANDSTILL_SPEED - value) * 100) / diff;
        }
    }

    @Override
    public void goToSpeed(int speed, int time) {
        goToSpeedLeft(speed, time);
        goToSpeedRight(speed, time);
    }

    @Override
    public void goToSpeedLeft(int speed, int time) {

        if (speed > 100) {
            System.out.println("Speed was higher than 100!");
            speed = 100;
        } else if (speed < -100) {
            System.out.println("Speed was lower than -100!");
            speed = -100;
        }

        this.wantedSpeedLeft = reverseValue(percentToValue(speed));
        int diff = this.wantedSpeedLeft - STANDSTILL_SPEED;

        int steps = diff / STEP_SIZE_LEFT;

        if (time < steps) {
            time = steps;
        }

        timerLeft.setInterval(time/steps);
        timerIsEnabledLeft = true;

    }

    public void goToSpeedRight(int speed, int time) {

        if (speed > 100) {
            System.out.println("Speed was higher than 100!");
            speed = 100;
        } else if (speed < -100) {
            System.out.println("Speed was lower than -100!");
            speed = -100;
        }

        this.wantedSpeedRight = percentToValue(speed);
        int diff = this.wantedSpeedRight - STANDSTILL_SPEED;

        int steps = diff / STEP_SIZE_RIGHT;

        if (time < steps) {
            time = steps;
        }

        timerLeft.setInterval(time/steps);
        timerIsEnabledRight = true;

    }

    private void goToSpeedStep(Servo servo, int stepSize) {
        int newSpeed = servo.getPulseWidth() + stepSize;
        if (newSpeed > MAX_FORWARD_SPEED) {
            newSpeed = MAX_FORWARD_SPEED;
        } else if (newSpeed < MAX_BACKWARD_SPEED){
            newSpeed = MAX_BACKWARD_SPEED;
        }
        servo.update(newSpeed);
    }

    @Override
    public void emergencyStop() {

    }

    @Override
    public int getSpeedLeft() {
        return valueToPercent(reverseValue(servoLeft.getPulseWidth()));
    }

    @Override
    public int getSpeedRight() {
        return valueToPercent(servoRight.getPulseWidth());
    }

    @Override
    public void update() {
        if (timerIsEnabledRight && timerRight.timeout()) {
            if (this.servoRight.getPulseWidth() != wantedSpeedRight) {
                goToSpeedStep(servoRight, STEP_SIZE_RIGHT);
            } else {
                timerIsEnabledRight = false;
            }

        }
        if (timerIsEnabledLeft && timerLeft.timeout()) {
            if (this.servoLeft.getPulseWidth() != wantedSpeedLeft) {
                goToSpeedStep(servoLeft, STEP_SIZE_LEFT);
            } else {
                timerIsEnabledLeft = false;
            }

        }
    }
}




/*
package Hardware;

        import TI.Servo;
        import TI.Timer;
        import Utils.Updatable;
//TODO make interface "Motors" and refactor this to "Servos" implementing "Motors"
public class Motors implements Updatable {
    private Servo servoLeft;
    private Servo servoRight;
    private int stepSize;
    private Timer servoTimer;
    private boolean acceleratingLeft;
    private boolean acceleratingRight;
    private int speedLeft;
    private int speedRight;
    private int timeBetweenSteps;

    public Motors(int stepSize) {
        this.servoLeft = new Servo(12);
        this.servoRight = new Servo(13);
        this.stepSize = stepSize;
        this.servoTimer = new Timer(0);
        this.acceleratingLeft = false;
        this.acceleratingRight = false;
        this.speedLeft = 1500;
        this.speedRight = 1500;
        this.timeBetweenSteps = 10;
    }

    public Motors() {
        this(5);
    }

    public void emergencyBrake() {
        drive(1500);
    }

    */
/**
     * Goes to the desired speed instantly.
     * @param speed speed witch the boebot wil drive at
     *//*

    public void drive(int speed) {
        driveLeft(speed);
        driveRight(speed);
    }


    public void driveLeft(int speed) {
        this.servoLeft.update(((speed - 1500) * -1) + 1500);
        this.speedLeft = ((speed - 1500) * -1) + 1500;
        this.acceleratingLeft = false;
    }

    public void driveRight(int speed) {
        this.servoRight.update(speed);
        this.speedRight = speed;
        this.acceleratingRight = false;
    }

    */
/**
     * Changes the speed one step closer to the wanted speed.
     * @param speed speed the boebot wants to drive at
     * @param servo servo witch speed wil be changed
     * @return boolean if the current speed is the wanted speed
     *//*

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

    */
/**
     * Repeats the goToSpeedOneStep until the speed is correct.
     * @param speed speed the boebot wants to drive at
     * @param timeBetweenSteps time taken between every increase of speed
     *//*

    public void goToSpeed(int speed, int timeBetweenSteps) {
        if (speed >= 1300 && speed <= 1700) {
            boolean leftIsSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoLeft);
            boolean rightIsSpeed = goToSpeedOneStep(speed, this.servoRight);
            if (!(leftIsSpeed && rightIsSpeed)) {
                this.servoTimer.setInterval(timeBetweenSteps);
                this.speedLeft = speed;
                this.speedRight = speed;
                this.acceleratingLeft = true;
                this.acceleratingRight = true;
                this.timeBetweenSteps = timeBetweenSteps;
            } else {
                this.acceleratingLeft = false;
                this.acceleratingRight = false;
            }
        } else {
            this.acceleratingLeft = false;
            this.acceleratingRight = false;
            System.out.println("impossible speed");
        }
    }

    */
/**
     * The same as goToSpeed, but only for the left servo.
     * @param speed speed the boebot wants to drive at
     * @param timeBetweenSteps time taken between every increase of speed
     *//*

    public void goToSpeedLeft (int speed, int timeBetweenSteps) {
        if (speed >= 1300 && speed <= 1700) {
            boolean isSpeed = goToSpeedOneStep(((speed - 1500) * -1) + 1500, this.servoLeft);
            if (!isSpeed) {
                this.servoTimer.setInterval(timeBetweenSteps);
                this.speedLeft = speed;
                this.acceleratingLeft = true;
                this.timeBetweenSteps = timeBetweenSteps;
            } else {
                this.acceleratingLeft = false;
            }
        } else {
            this.acceleratingLeft = false;
            System.out.println("impossible speed");
        }
    }

    */
/**
     * The same as goToSpeed, but only for the right servo.
     * @param speed speed the boebot wants to drive at
     * @param timeBetweenSteps time taken between every increase of speed
     *//*

    public void goToSpeedRight (int speed, int timeBetweenSteps) {
        if (speed >= 1300 && speed <= 1700) {
            boolean isSpeed = goToSpeedOneStep(speed, this.servoRight);
            if (!isSpeed) {
                this.servoTimer.setInterval(timeBetweenSteps);
                this.speedRight = speed;
                this.acceleratingRight = true;
                this.timeBetweenSteps = timeBetweenSteps;
            } else {
                this.acceleratingRight = false;
            }
        } else {
            this.acceleratingRight = false;
            System.out.println("inpossible speed");
        }
    }

    public int getSpeedLeft() {
        return this.speedLeft;
    }

    public int getSpeedRight() {
        return this.speedRight;
    }

    public int getStepSize() {
        return this.stepSize;
    }

    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    @Override
    public void update() {
        if (this.servoTimer.timeout()) {
            if (this.acceleratingLeft && this.acceleratingRight) {
                goToSpeed(this.speedLeft, this.timeBetweenSteps);
            } else if (this.acceleratingLeft) {
                goToSpeedLeft(this.speedLeft, this.timeBetweenSteps);
            } else if (this.acceleratingRight) {
                goToSpeedRight(this.speedRight, this.timeBetweenSteps);
            }
            this.servoTimer.mark();
        }
    }
}

*/
