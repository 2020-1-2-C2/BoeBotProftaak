package Hardware;

import TI.Servo;
import TI.Timer;
import Utils.Motor;

import java.util.ArrayList;

//TODO make interface "Motors" and refactor this to "Servos" implementing "Motors"
public class ServoMotor implements Motor {
    private Servo servoLeft;
    private Servo servoRight;
    private Timer timerBoth;
    private Timer timerLeft;
    private Timer timerRight;
    private boolean timerIsEnabledBoth;
    private boolean timerIsEnabledLeft;
    private boolean timerIsEnabledRight;

    private final int STEP_SIZE_BOTH = 2;
    private int wantedSpeedLeft;
    private final int STEP_SIZE_LEFT = 2;
    private int wantedSpeedRight;
    private final int STEP_SIZE_RIGHT = 2;


    private final int MAX_FORWARD_SPEED = 1700;
    private final int MAX_BACKWARD_SPEED = 1300;
    private final int STANDSTILL_SPEED = 1500;

    public ServoMotor(Servo servoRight, Servo servoLeft) {
        this.servoLeft = servoLeft;
        this.servoRight = servoRight;
        this.timerLeft = new Timer(1000);
        this.timerRight = new Timer(1000);
        this.timerBoth = new Timer(1000);
        timerIsEnabledLeft = false;
        timerIsEnabledRight = false;
        timerIsEnabledBoth = false;
    }

    public ServoMotor() {
        this(new Servo(12), new Servo(13));
    }

    private int reverseValue(int value) {
        return ((value - STANDSTILL_SPEED) * -1) + STANDSTILL_SPEED;
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
        speed = validateSpeed(speed);
        this.wantedSpeedLeft = percentToValue(speed);
        this.wantedSpeedRight = reverseValue(percentToValue(speed));
        int diffRight = this.wantedSpeedRight - servoRight.getPulseWidth();
        int diffLeft = this.wantedSpeedLeft - servoLeft.getPulseWidth();
        int diff = diffRight > diffLeft ? diffRight : diffLeft;

        int steps = diff / STEP_SIZE_BOTH;

        if (time < steps) {
            time = steps;
        }

        if (steps != 0) {
            timerBoth.setInterval(time);
            timerIsEnabledBoth = true;
        }

    }

    private int validateSpeed(int speed) {
        if (speed > 100) {
            System.out.println("Speed was higher than 100!");
            speed = 100;
        } else if (speed < -100) {
            System.out.println("Speed was lower than -100!");
            speed = -100;
        }

        return speed;

    }

    @Override
    public void goToSpeedLeft(int speed, int time) {
        System.out.println("speed %: " + speed);

        speed = validateSpeed(speed);

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
        speed = validateSpeed(speed);

        this.wantedSpeedRight = reverseValue(percentToValue(speed));
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

    private void goToSpeedStep(ArrayList<Servo> servos, int stepSize, ArrayList<Integer> wantedSpeeds) {
        if (servos.size() != wantedSpeeds.size()) {
            return;
        }

        for (int i = 0; i < servos.size(); i++) {
            int step;
            if (servos.get(i).getPulseWidth() > wantedSpeeds.get(i)) {
                step = -stepSize;
            } else {
                step = stepSize;
            }

            int newSpeed = servos.get(i).getPulseWidth() + step;
            if (newSpeed > MAX_FORWARD_SPEED) {
                newSpeed = MAX_FORWARD_SPEED;
            } else if (newSpeed < MAX_BACKWARD_SPEED) {
                newSpeed = MAX_BACKWARD_SPEED;
            }
            servos.get(i).update(newSpeed);
        }
    }

    @Override
    public void emergencyStop() {
        this.timerIsEnabledRight = false;
        this.timerIsEnabledLeft = false;
        servoRight.update(STANDSTILL_SPEED);
        servoLeft.update(STANDSTILL_SPEED);
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
        if (timerIsEnabledBoth && timerBoth.timeout()) {
            if (this.servoRight.getPulseWidth() != wantedSpeedRight || this.servoLeft.getPulseWidth() != wantedSpeedLeft) {
                ArrayList<Servo> servos = new ArrayList<>();
                servos.add(servoRight);
                servos.add(servoLeft);
                ArrayList<Integer> wantedSpeeds = new ArrayList<>();
                wantedSpeeds.add(wantedSpeedRight);
                wantedSpeeds.add(wantedSpeedLeft);
                goToSpeedStep(servos, STEP_SIZE_BOTH, wantedSpeeds);
            } else {
                timerIsEnabledBoth = false;
            }
        }
        if (timerIsEnabledRight && timerRight.timeout()) {
            if (this.servoRight.getPulseWidth() != wantedSpeedRight) {
                ArrayList<Servo> servos = new ArrayList<>();
                servos.add(servoRight);
                ArrayList<Integer> wantedspeeds = new ArrayList<>();
                wantedspeeds.add(wantedSpeedRight);
                goToSpeedStep(servos, STEP_SIZE_RIGHT, wantedspeeds);
            } else {
                timerIsEnabledRight = false;
            }

        }
        if (timerIsEnabledLeft && timerLeft.timeout()) {
            if (this.servoLeft.getPulseWidth() != wantedSpeedLeft) {
                ArrayList<Servo> servos = new ArrayList<>();
                servos.add(servoLeft);
                ArrayList<Integer> wantedSpeeds = new ArrayList<>();
                wantedSpeeds.add(wantedSpeedLeft);
                goToSpeedStep(servos, STEP_SIZE_LEFT, wantedSpeeds);
            } else {
                timerIsEnabledLeft = false;
            }

        }
    }
}
