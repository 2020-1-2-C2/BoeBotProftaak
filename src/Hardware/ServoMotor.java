package Hardware;

import TI.Servo;
import TI.Timer;
import Utils.Motor;

import java.util.ArrayList;
import java.util.List;

//TODO make interface "Motors" and refactor this to "Servos" implementing "Motors"
public class ServoMotor implements Motor {
    private DirectionalServo servoLeft;
    private DirectionalServo servoRight;
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

    /**
     *
     * @param servoRight
     * @param servoLeft
     */
    public ServoMotor(DirectionalServo servoRight, DirectionalServo servoLeft) {
        this.servoLeft = servoLeft;
        this.servoRight = servoRight;
        this.timerLeft = new Timer(1000);
        this.timerRight = new Timer(1000);
        timerIsEnabledLeft = false;
        timerIsEnabledRight = false;
    }

    private int percentToValue(int percent) {
        int diff = MAX_FORWARD_SPEED - STANDSTILL_SPEED;
        return ((diff / 100) * percent) + STANDSTILL_SPEED;
    }

    private int valueToPercent(int value) {
        int diff = MAX_FORWARD_SPEED - STANDSTILL_SPEED;
        return -((value - STANDSTILL_SPEED) * 100) / diff;
    }

    @Override
    public void goToSpeed(int speed, int time) {
        goToSpeedLeft(speed, time);
        goToSpeedRight(speed, time);
        timerRight.mark();
        timerLeft.mark();
    }

    @Override
    public void goToSpeedLeft(int speed, int time) {
        System.out.println("speed %: " + speed);

        if (speed > 100) {
            System.out.println("Speed was higher than 100!");
            speed = 100;
        } else if (speed < -100) {
            System.out.println("Speed was lower than -100!");
            speed = -100;
        }

        this.wantedSpeedLeft = percentToValue(speed);
        System.out.println("speed value: " + wantedSpeedLeft);
        int diff = this.wantedSpeedLeft - servoLeft.getPulseWidth();

        int steps = diff / STEP_SIZE_LEFT;

        if (time < steps) {
            time = steps;
        }

        if (steps != 0 ) {
            timerLeft.setInterval(time / steps);
            timerIsEnabledLeft = true;
        }

    }

    public void goToSpeedRight(int speed, int time) {

        System.out.println("speed %: " + speed);
        if (speed > 100) {
            System.out.println("Speed was higher than 100!");
            speed = 100;
        } else if (speed < -100) {
            System.out.println("Speed was lower than -100!");
            speed = -100;
        }

        this.wantedSpeedRight = percentToValue(speed);
        System.out.println("speed value: " + wantedSpeedRight);
        int diff = this.wantedSpeedRight - servoRight.getPulseWidth();

        int steps = diff / STEP_SIZE_RIGHT;

        if (time < steps) {
            time = steps;
        }

        if (steps != 0 ) {
            timerRight.setInterval(time / steps);
            timerIsEnabledRight = true;
        }

    }

    private void goToSpeedStep(List<Servo> servos, int stepSize, int wantedSpeed) {

        for (Servo servo : servos) {
            int step;
            if (servo.getPulseWidth() > wantedSpeed) {
                step = -stepSize;
            } else {
                step = stepSize;
            }

            //System.out.println(wantedSpeed);

            int newSpeed = servo.getPulseWidth() + step;
            if (newSpeed > MAX_FORWARD_SPEED) {
                newSpeed = MAX_FORWARD_SPEED;
            } else if (newSpeed < MAX_BACKWARD_SPEED) {
                newSpeed = MAX_BACKWARD_SPEED;
            }
            servo.update(newSpeed);
        }
    }

    @Override
    public void emergencyStop() {
        servoRight.update(STANDSTILL_SPEED);
        servoLeft.update(STANDSTILL_SPEED);

        // resetting the internal speed within the software to match the emergency stop
        wantedSpeedLeft = percentToValue(0);
        wantedSpeedRight = percentToValue(0);
    }

    @Override
    public int getSpeedLeft() {
        return valueToPercent(servoLeft.getPulseWidth());
    }

    @Override
    public int getSpeedRight() {
        return valueToPercent(servoRight.getPulseWidth());
    }

    @Override
    public void update() {
        if (timerIsEnabledLeft && timerIsEnabledRight && timerRight.timeout()) {
            timerLeft.mark();
            timerRight.mark();
            if (this.servoRight.getPulseWidth() == wantedSpeedRight) {
                timerIsEnabledRight = false;
            } else if (this.servoLeft.getPulseWidth() == wantedSpeedLeft) {
                timerIsEnabledLeft = false;
            } else {
                List<Servo> servos = new ArrayList<>();
                servos.add(servoLeft);
                servos.add(servoRight);
                goToSpeedStep(servos, STEP_SIZE_RIGHT, wantedSpeedRight);
            }
        } else if (timerIsEnabledRight && timerRight.timeout()) {
            if (this.servoRight.getPulseWidth() != wantedSpeedRight) {
                List<Servo> servos = new ArrayList<>();
                servos.add(servoRight);
                goToSpeedStep(servos, STEP_SIZE_RIGHT, wantedSpeedRight);
            } else {
                timerIsEnabledRight = false;
            }

        } else if (timerIsEnabledLeft && timerLeft.timeout()) {
            if (this.servoLeft.getPulseWidth() != wantedSpeedLeft) {
                List<Servo> servos = new ArrayList<>();
                servos.add(servoLeft);
                goToSpeedStep(servos, STEP_SIZE_LEFT, wantedSpeedLeft);
            } else {
                timerIsEnabledLeft = false;
            }

        }
    }
}

