package Hardware;

import TI.Servo;
import Utils.Motor;
import Utils.TimerWithState;
import Utils.Updatable;

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
        this.timerLeft.setOn(false);
        this.timerRight.setOn(false);
    }

    /**
     * Converts a speed percentage to a signal length which matches that speed percentage to give to the servo motors.
     *
     * @param percent Percentage speed of the maximum.
     * @return Signal length which can be sent to the servo motors to set the speed.
     */
    private int percentToValue(int percent) {
        int diff = this.MAX_FORWARD_SPEED - this.STANDSTILL_SPEED;
        return ((diff / 100) * percent) + this.STANDSTILL_SPEED;
    }

    /**
     * Converts a signal length from the servo motors to a matching percentage of the maximum speed.
     *
     * @param value Signal length used with the servo motors.
     * @return Percentage speed of the maximum.
     */
    private int valueToPercent(int value) {
        int diff = this.MAX_FORWARD_SPEED - this.STANDSTILL_SPEED;
        return -((value - this.STANDSTILL_SPEED) * 100) / diff;
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
        this.timerRight.mark();
        this.timerLeft.mark();
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
        System.out.println("speed value: " + this.wantedSpeedLeft);
        int diff = this.wantedSpeedLeft - this.servoLeft.getPulseWidth();

        int steps = diff / this.STEP_SIZE_LEFT;

        if (time < steps) {
            time = steps;
        }

        if (steps != 0) {
            this.timerLeft.setInterval(time / steps);
            this.timerLeft.setOn(true);
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
        System.out.println("speed value: " + this.wantedSpeedRight);
        int diff = this.wantedSpeedRight - this.servoRight.getPulseWidth();

        int steps = diff / this.STEP_SIZE_RIGHT;

        if (time < steps) {
            time = steps;
        }

        if (steps != 0) {
            this.timerRight.setInterval(time / steps);
            this.timerRight.setOn(true);
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
            if (newSpeed > this.MAX_FORWARD_SPEED) {
                newSpeed = this.MAX_FORWARD_SPEED;
            } else if (newSpeed < this.MAX_BACKWARD_SPEED) {
                newSpeed = this.MAX_BACKWARD_SPEED;
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
        this.servoRight.update(this.STANDSTILL_SPEED);
        this.servoLeft.update(this.STANDSTILL_SPEED);

        // Resetting the internal speed within the software to match the emergency stop.
        this.wantedSpeedLeft = percentToValue(0);
        this.wantedSpeedRight = percentToValue(0);
    }

    /**
     * Overrides getSpeedLeft() in Motor.java.
     * @return The pulsewidth of the left servomotor converted to a percentage speed value.
     * @see ServoMotor#getSpeedLeft()
     */
    @Override
    public int getSpeedLeft() {
        return valueToPercent(this.servoLeft.getPulseWidth());
    }

    /**
     * Overrides getSpeedRight() in Motor.java.
     * @return The pulsewidth of the right servomotor converted to a percentage speed value.
     * @see ServoMotor#getSpeedRight()
     */
    @Override
    public int getSpeedRight() {
        return valueToPercent(this.servoRight.getPulseWidth());
    }

    /**
     * Overrides the update method.
     * Update the servo motors to go to the desired speed in a small step (2ms pulse width)
     * It will make a step each time the timer for the servo motor times out
     * As soon as the actual speed matches the desired speed the timer is turned off
     * @see Updatable#update()
     */
    @Override
    public void update() {
        // Update both motors
        // if both timers are enabled then they should both be on synchronous timeout, so if one has a timeout then both motors can be updated.
        if (this.timerLeft.isOn() && this.timerRight.isOn() && this.timerRight.timeout()) {
            this.timerLeft.mark();
            this.timerRight.mark();
            if (this.servoRight.getPulseWidth() == this.wantedSpeedRight) {
                this.timerRight.setOn(false);
            } else if (this.servoLeft.getPulseWidth() == this.wantedSpeedLeft) {
                this.timerLeft.setOn(false);
            } else {
                List<Servo> servos = new ArrayList<>();
                servos.add(this.servoLeft);
                servos.add(this.servoRight);
                goToSpeedStep(servos, this.STEP_SIZE_RIGHT, this.wantedSpeedRight);
            }

            // Update just the right motor if the timer is enabled and timed out.
        } else if (this.timerRight.isOn() && this.timerRight.timeout()) {
            if (this.servoRight.getPulseWidth() != this.wantedSpeedRight) {
                List<Servo> servos = new ArrayList<>();
                servos.add(this.servoRight);
                goToSpeedStep(servos, this.STEP_SIZE_RIGHT, this.wantedSpeedRight);
            } else {
                this.timerRight.setOn(false);
            }

            // Update just the left motor if the timer is enabled and timed out.
        } else if (this.timerLeft.isOn() && this.timerLeft.timeout()) {
            if (this.servoLeft.getPulseWidth() != this.wantedSpeedLeft) {
                List<Servo> servos = new ArrayList<>();
                servos.add(this.servoLeft);
                goToSpeedStep(servos, this.STEP_SIZE_LEFT, this.wantedSpeedLeft);
            } else {
                this.timerLeft.setOn(false);
            }
        }
    }
}

