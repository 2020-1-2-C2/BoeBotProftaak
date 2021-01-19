package hardware;

import TI.Servo;
import utils.Motor;
import utils.TimerWithState;
import utils.Updatable;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which controls two DirectionServos
 *
 * Implements the Motor interface to ensure easy switching of hardware
 *
 * Contains methods to set the desired speed of the motors and to stop the motors
 *
 * Implements the Updatable interface,
 * the update method incrementally changes the speed of the DirectionServos towards the desired speed,
 * until the desired speed has been achieved.
 *
 * @see Motor
 * @see DirectionalServo
 * @version 1.0
 * @author Martijn de Kam, Berend de Groot, Meindert Kempe, Tom Martens, Lars Hoendervangers
 */
public class ServoMotor implements Motor, Updatable {
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

    //Test attributes
    private final String LEFT_SIDE_STRINGID = "Left";
    private final String RIGHT_SIDE_STRINGID = "Right";


    /**
     * Constructor for ServoMotor.java.
     * @param servoRight The right servo motor of the bot.
     * @param servoLeft  The left servo motor of the bot.
     * @see DirectionalServo
     */
    public ServoMotor(DirectionalServo servoRight, DirectionalServo servoLeft) {
        this.servoLeft = servoLeft;
        this.servoRight = servoRight;
        this.timerLeft = new TimerWithState(10);
        this.timerRight = new TimerWithState(10);
        this.timerLeft.setOn(false);
        this.timerRight.setOn(false);
    }

    /**
     * Converts a speed percentage to a signal length which matches that speed percentage to give to the servo motors.
     * @param percent Percentage speed of the maximum.
     * @return Signal length which can be sent to the servo motors to set the speed.
     */
    private int percentToValue(int percent) {
        int diff = this.MAX_FORWARD_SPEED - this.STANDSTILL_SPEED;
        return ((diff / 100) * percent) + this.STANDSTILL_SPEED;
    }

    /**
     * Converts a signal length from the servo motors to a matching percentage of the maximum speed.
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
//        System.out.println("goToSpeed - test");
        goToSpeedLeft(speed, time);
        goToSpeedRight(speed, time);
        this.timerRight.mark();
        this.timerLeft.mark();
    }

    /**
     * Overrides <code>goToSpeedLeft</code> in <a href="{@docRoot}/hardware/Motor.html">Motor</a>.
     * Set the desired speed (percentage of maximum) for the left motor to go to.
     * Runs through this.goToSpeedSide with the parameter "side" set to left.
     * @param speed Percentage of the maximum speed (100 max forwards, -100 max backwards).
     * @param time  Time in milliseconds over which to accelerate.
     * @see Motor#goToSpeedLeft(int, int)
     * @see #goToSpeedSide(int, int, String)
     */
    @Override
    public void goToSpeedLeft(int speed, int time) {
        this.goToSpeedSide(speed, time, LEFT_SIDE_STRINGID);
    }

    /**
     * Overrides goToSpeedRight in <a href="{@docRoot}/hardware/Motor.html">Motor</a>.
     * Sets the desired speed (percentage of maximum) for the right motor to go to.
     * Runs through this.goToSpeedSide with the parameter "side" set to right.
     * @param speed Percentage of the maximum speed (100 max forwards, -100 max backwards).
     * @param time  Time in milliseconds over which to accelerate.
     * @see Motor#goToSpeedRight(int, int)
     * @see #goToSpeedSide(int, int, String)
     */
    @Override
    public void goToSpeedRight(int speed, int time) {
        this.goToSpeedSide(speed, time, RIGHT_SIDE_STRINGID);
    }

    /**
     * Set the servo motor on the given side to the given speed.
     * @param speed wanted speed
     * @param time duration of the acceleration
     * @param side side of the servo motor
     */
    private void goToSpeedSide(int speed, int time, String side){
//        System.out.println("WantedSpeedLeft: " + this.wantedSpeedLeft);
//        System.out.println("WantedSpeedRight: " + this.wantedSpeedRight);
        if (speed > 100) {
            speed = 100;
        } else if (speed < -100) {
            speed = -100;
        }

        if (side.equals(RIGHT_SIDE_STRINGID)){
            System.out.println("SIDE: " + RIGHT_SIDE_STRINGID);
            this.wantedSpeedRight = percentToValue(speed);
            int diff = this.wantedSpeedRight - this.servoRight.getPulseWidth();
            int steps = diff / this.STEP_SIZE_RIGHT;

            if (time < steps) {
//                System.out.println("Time < Steps: " + RIGHT_SIDE_STRINGID);
                time = steps;
            }

            if (steps != 0) {
//                System.out.println("Steps != 0: " + RIGHT_SIDE_STRINGID);
//                this.timerRight.setInterval(time / steps);
                this.timerRight.setOn(true);
            }
        } else if (side.equals(LEFT_SIDE_STRINGID)) {
            System.out.println("SIDE: " + LEFT_SIDE_STRINGID);
            this.wantedSpeedLeft = percentToValue(speed);
            int diff = this.wantedSpeedLeft - this.servoLeft.getPulseWidth();
            int steps = diff / this.STEP_SIZE_LEFT;

            if (time < steps) {
//                System.out.println("Time < Steps: " + LEFT_SIDE_STRINGID);
                time = steps;
            }

            if (steps != 0) {
//                System.out.println("Steps != 0: " + LEFT_SIDE_STRINGID);
//                this.timerLeft.setInterval(time / steps);
                this.timerLeft.setOn(true);
            }
        }
    }

    /**
     * Update both servos to change the servo motor speed by one step-size.
     * Can't go higher or lower than the maximum speeds.
     *
     * @param servos      Both servos of the bot.
     * @param stepSize    Step size to change the servo motor pulse-width with.
     * @param wantedSpeed Desired speed to change to.
     */
    private void goToSpeedStep(List<Servo> servos, int stepSize, int wantedSpeed) {
        System.out.println("Buiten lus");
        for (Servo servo : servos) {
            int step;
            // If the current speed is greater then it needs to be decreased, else it needs to be increased.
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
            System.out.println("Servos going to new speed: " + newSpeed);
        }
    }

    /**
     * Overrides <code>emergencyStop()</code> in <a href="{@docRoot}/hardware/Motor.html">Motor</a>.
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
     * Stop immediatly without it being an emergency
     */
    @Override
    public void immediateStop() {
        this.servoRight.update(this.STANDSTILL_SPEED);
        this.servoLeft.update(this.STANDSTILL_SPEED);

        // Resetting the internal speed within the software to match the emergency stop.
        this.wantedSpeedLeft = percentToValue(0);
        this.wantedSpeedRight = percentToValue(0);
    }

    /**
     * Overrides getSpeedLeft() in <a href="{@docRoot}/hardware/Motor.html">Motor</a>.
     * @return The pulsewidth of the left servomotor converted to a percentage speed value.
     * @see ServoMotor#getSpeedLeft()
     */
    @Override
    public int getSpeedLeft() {
        return valueToPercent(this.servoLeft.getPulseWidth());
    }

    /**
     * Overrides getSpeedRight() in <a href="{@docRoot}/hardware/Motor.html">Motor</a>.
     * @return The pulsewidth of the right servomotor converted to a percentage speed value.
     * @see ServoMotor#getSpeedRight()
     */
    @Override
    public int getSpeedRight() {
        return valueToPercent(this.servoRight.getPulseWidth());
    }

    /**
     * Overrides the <code>update()</code> method.
     * Update the servo motors to go to the desired speed in a small step (2ms pulse width)
     * It will make a step each time the timer for the servo motor times out
     * As soon as the actual speed matches the desired speed the timer is turned off
     * @see Updatable#update()
     */
    @Override
    public void update() {
//        System.out.println("Update ServoMotor - Left timer: " + this.timerLeft.isOn() + "Right timer: " + this.timerRight.isOn());

//        if (this.timerLeft.isOn()){
//            System.out.println("Ik had gelijk, anders ga ik huilen.");
//
//            if (this.timerLeft.timeout()){
//                System.out.println("TimerLeft ging yeet.");
//            }
//
//        }

//        if (this.timerRight.timeout()){
//            System.out.println("Bingo: Right");
//            if (this.timerRight.isOn()){
//                System.out.println("TimerRight staan aan!");
//            }
//        }
//        if (this.timerLeft.timeout()){
//            System.out.println("Bingo: Left");
//            if (this.timerLeft.isOn()){
//                System.out.println("TimerLeft staan aan!");
//            }
//        }

        // If both timers are enabled then they should both be on synchronous timeout, so if one has a timeout then both motors can be updated.
        if (this.timerLeft.isOn() && this.timerRight.isOn() && this.timerRight.timeout()) {
            System.out.println("this.timerLeft.isOn() && this.timerRight.isOn() && this.timerRight.timeout()");
            this.timerLeft.mark();
            this.timerRight.mark();
            if (this.servoRight.getPulseWidth() == this.wantedSpeedRight) {
                this.timerRight.setOn(false);
            } else if (this.servoLeft.getPulseWidth() == this.wantedSpeedLeft) {
                this.timerLeft.setOn(false);
            } else {
                System.out.println("ELSE02 this.timerLeft.isOn() && this.timerRight.isOn() && this.timerRight.timeout()");
                List<Servo> servos = new ArrayList<>();
                servos.add(this.servoLeft);
                servos.add(this.servoRight);
                goToSpeedStep(servos, this.STEP_SIZE_RIGHT, this.wantedSpeedRight);
            }

            // Update just the right motor if the timer is enabled and timed out.
        } else if (this.timerRight.isOn() && this.timerRight.timeout()) {
            System.out.println("this.timerRight.isOn() && this.timerRight.timeout()");
            if (this.servoRight.getPulseWidth() != this.wantedSpeedRight) {
                System.out.println("this.servoRight.getPulseWidth() != this.wantedSpeedRight");
                List<Servo> servos = new ArrayList<>();
                servos.add(this.servoRight);
                goToSpeedStep(servos, this.STEP_SIZE_RIGHT, this.wantedSpeedRight);
            } else {
                System.out.println("ELSE03");
                this.timerRight.setOn(false);
            }

            // Update just the left motor if the timer is enabled and timed out.
        } else if (this.timerLeft.isOn() && this.timerLeft.timeout()) {
            System.out.println("this.timerLeft.isOn() && this.timerLeft.timeout()");
            if (this.servoLeft.getPulseWidth() != this.wantedSpeedLeft) {
                System.out.println("this.servoLeft.getPulseWidth() != this.wantedSpeedLeft");
                List<Servo> servos = new ArrayList<>();
                servos.add(this.servoLeft);
                goToSpeedStep(servos, this.STEP_SIZE_LEFT, this.wantedSpeedLeft);
            } else {
                this.timerLeft.setOn(false);
            }
        }
    }
}