package Hardware;

import TI.Servo;
import Utils.Motor;
import Utils.TimerWithState;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO WRITE DOCUMENTATION PLEASEEEEEE
 * @see Motor
 * @see DirectionalServo
 * @version 1.0
 */
public class ServoMotor implements Motor {
    private DirectionalServo servoLeft;
    private DirectionalServo servoRight;
    private TimerWithState timerLeft;
    private TimerWithState timerRight;

    private int wantedSpeedLeft;
    private final int STEP_SIZE_LEFT = 2;
    private int wantedSpeedRight;
    private final int STEP_SIZE_RIGHT = 2;

    private final int MAX_FORWARD_SPEED = 1700;
    private final int MAX_BACKWARD_SPEED = 1300;
    private final int STANDSTILL_SPEED = 1500;

    /**
     * Constructor for ServoMotor.java.
     * @param servoRight The right servo motor of the bot.
     * @param servoLeft  The left servo motor of the bot.
     * @see DirectionalServo
     */
    public ServoMotor(DirectionalServo servoRight, DirectionalServo servoLeft) {
        this.servoLeft = servoLeft;
        this.servoRight = servoRight;
        this.timerLeft = new TimerWithState(1000);
        this.timerRight = new TimerWithState(1000);
        timerLeft.setOn(false);
        timerRight.setOn(false);
    }

    /**
     * Converts a speed percentage to a signal length which matches that speed percentage to give to the servo motors.
     *
     * @param percent Percentage speed of the maximum.
     * @return Signal length which can be sent to the servo motors to set the speed.
     */
    private int percentToValue(int percent) {
        int diff = MAX_FORWARD_SPEED - STANDSTILL_SPEED;
        return ((diff / 100) * percent) + STANDSTILL_SPEED;
    }

    /**
     * Converts a signal length from the servo motors to a matching percentage of the maximum speed.
     *
     * @param value Signal length used with the servo motors.
     * @return Percentage speed of the maximum.
     */
    private int valueToPercent(int value) {
        int diff = MAX_FORWARD_SPEED - STANDSTILL_SPEED;
        return -((value - STANDSTILL_SPEED) * 100) / diff;
    }

    /**
     * Overrides goToSpeed in Motor.java.
     * Set the desired speed (percentage of maximum) for both motors to go to.
     *
     * @param speed Percentage of the maximum speed (100 max forwards, -100 max backwards).
     * @param time  Time in milliseconds over which to accelerate.
     * @see Motor#goToSpeed(int, int)
     */
    @Override
    public void goToSpeed(int speed, int time) {
        goToSpeedLeft(speed, time);
        goToSpeedRight(speed, time);
        timerRight.mark();
        timerLeft.mark();
    }

    /**
     * Overrides goToSpeedLeft in Motor.java.
     * Set the desired speed (percentage of maximum) for the left motor to go to.
     *
     * @param speed Percentage of the maximum speed (100 max forwards, -100 max backwards).
     * @param time  Time in milliseconds over which to accelerate.
     * @see Motor#goToSpeedLeft(int, int)
     */
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

        if (steps != 0) {
            timerLeft.setInterval(time / steps);
            timerLeft.setOn(true);
        }

    }

    /**
     * Overrides goToSpeedRight in Motor.java.
     * Sets the desired speed (percentage of maximum) for the right motor to go to.
     *
     * @param speed Percentage of the maximum speed (100 max forwards, -100 max backwards).
     * @param time  Time in milliseconds over which to accelerate.
     * @see Motor#goToSpeedRight(int, int)
     */
    @Override
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

        if (steps != 0) {
            timerRight.setInterval(time / steps);
            timerRight.setOn(true);
        }

    }

    /**
     * Update both servos to change the servo motor speed by one stepsize.
     * Can't go higher or lower than the maximum speeds.
     *
     * @param servos      Both servos of the bot.
     * @param stepSize    Step size to change the servo motor pulsewidth with.
     * @param wantedSpeed Desired speed to change to.
     */
    private void goToSpeedStep(List<Servo> servos, int stepSize, int wantedSpeed) {
        for (Servo servo : servos) {
            int step;
            // if the current speed is greater then it needs to be decreased, else it needs to be increased
            if (servo.getPulseWidth() > wantedSpeed) {
                step = -stepSize;
            } else {
                step = stepSize;
            }

            int newSpeed = servo.getPulseWidth() + step;
            if (newSpeed > MAX_FORWARD_SPEED) {
                newSpeed = MAX_FORWARD_SPEED;
            } else if (newSpeed < MAX_BACKWARD_SPEED) {
                newSpeed = MAX_BACKWARD_SPEED;
            }
            servo.update(newSpeed);
        }
    }

    /**
     * Overrides emergencyStop() in Motor.java.
     * Stop as soon as possible.
     * Sets the servo motors to stand still and changes the internal speed to match this.
     * @see Motor#emergencyStop()
     */
    @Override
    public void emergencyStop() {
        servoRight.update(STANDSTILL_SPEED);
        servoLeft.update(STANDSTILL_SPEED);

        // resetting the internal speed within the software to match the emergency stop
        wantedSpeedLeft = percentToValue(0);
        wantedSpeedRight = percentToValue(0);
    }

    /**
     * Overrides getSpeedLeft() in Motor.java.
     * @return The pulsewidth of the left servomotor converted to a percentage speed value.
     * @see ServoMotor#getSpeedLeft()
     */
    @Override
    public int getSpeedLeft() {
        return valueToPercent(servoLeft.getPulseWidth());
    }

    /**
     * Overrides getSpeedRight() in Motor.java.
     * @return The pulsewidth of the right servomotor converted to a percentage speed value.
     * @see ServoMotor#getSpeedRight()
     */
    @Override
    public int getSpeedRight() {
        return valueToPercent(servoRight.getPulseWidth());
    }

    /**
     * Overrides the update method.
     * Update the servo motors to go to the desired speed in a small step (2ms pulse width)
     * It will make a step each time the timer for the servo motor times out
     * As soon as the actual speed matches the desired speed the timer is turned off
     * @see Utils.Updatable
     */
    @Override
    public void update() {
        // Update both motors
        // if both timers are enabled then they should both be on synchronous timeout, so if one has a timeout then both motors can be updated.
        if (timerLeft.isOn() && timerRight.isOn() && timerRight.timeout()) {
            timerLeft.mark();
            timerRight.mark();
            if (this.servoRight.getPulseWidth() == wantedSpeedRight) {
                timerRight.setOn(false);
            } else if (this.servoLeft.getPulseWidth() == wantedSpeedLeft) {
                timerLeft.setOn(false);
            } else {
                List<Servo> servos = new ArrayList<>();
                servos.add(servoLeft);
                servos.add(servoRight);
                goToSpeedStep(servos, STEP_SIZE_RIGHT, wantedSpeedRight);
            }

            // Update just the right motor if the timer is enabled and timed out.
        } else if (timerRight.isOn() && timerRight.timeout()) {
            if (this.servoRight.getPulseWidth() != wantedSpeedRight) {
                List<Servo> servos = new ArrayList<>();
                servos.add(servoRight);
                goToSpeedStep(servos, STEP_SIZE_RIGHT, wantedSpeedRight);
            } else {
                timerRight.setOn(false);
            }

            // Update just the left motor if the timer is enabled and timed out.
        } else if (timerLeft.isOn() && timerLeft.timeout()) {
            if (this.servoLeft.getPulseWidth() != wantedSpeedLeft) {
                List<Servo> servos = new ArrayList<>();
                servos.add(servoLeft);
                goToSpeedStep(servos, STEP_SIZE_LEFT, wantedSpeedLeft);
            } else {
                timerLeft.setOn(false);
            }
        }
    }
}

