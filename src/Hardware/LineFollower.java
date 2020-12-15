package Hardware;

import TI.BoeBot;
import TI.Servo;
import Utils.Updatable;

public class LineFollower implements Updatable {

    private int leftServoPin;
    private int rightServoPin;

    private int leftLineSensorPin;
    private int rightLineSensorPin;
    private int centralLineSensorPin;

    private final int servoSpeedDefault = 100;
    private final int servoZeroSpeed = 1500;
    private final int turningSpeed = 100;
    private final int turningTweak = 50;
    private final int sensorTweak = 1200;
    private boolean turning = false;

    private Servo servoRechts;
    private Servo servoLinks;

    public LineFollower(int leftServoPin, int rightServoPin, int leftLineSensorPin, int rightLineSensorPin) {
        this.leftServoPin = leftServoPin;
        this.rightServoPin = rightServoPin;
        this.leftLineSensorPin = leftLineSensorPin;
        this.rightLineSensorPin = rightLineSensorPin;

        this.centralLineSensorPin = -1;

        servoRechts = new Servo(rightServoPin);
        servoRechts.update(servoSpeedDefault);
        servoLinks = new Servo(leftServoPin);
        servoLinks.update(servoSpeedDefault);
    }

    public LineFollower(int leftServoPin, int rightServoPin, int leftLineSensorPin, int rightLineSensorPin, int centralLineSensorPin) {
        this(leftServoPin, rightServoPin, leftLineSensorPin, rightLineSensorPin);
        this.centralLineSensorPin = centralLineSensorPin;
    }

    private void straightAhead() {
        servoLinks.update(servoZeroSpeed - servoSpeedDefault);
        servoRechts.update(servoZeroSpeed + servoSpeedDefault);
    }

    private void turnFullRight() {
        servoRechts.update(servoRechts.getPulseWidth() + turningSpeed);
        servoLinks.update(servoLinks.getPulseWidth() + turningSpeed + turningTweak);
    }

    private void turnSlightRight() {
        servoRechts.update(servoRechts.getPulseWidth() + (turningSpeed / 3));
        servoLinks.update(servoZeroSpeed);
    }

    private void turnFullLeft() {
        servoLinks.update(servoLinks.getPulseWidth() - turningSpeed);
        servoRechts.update(servoRechts.getPulseWidth() - turningSpeed - turningTweak);
    }

    private void turnSlightLeft() {
        servoRechts.update(servoRechts.getPulseWidth() - (turningSpeed / 3));
        servoLinks.update(servoZeroSpeed);
    }

    private boolean leftSeesBlack() {
        if (BoeBot.analogRead(leftLineSensorPin) > sensorTweak) {
            return true;
        } else {
            return false;
        }

    }

    private boolean rightSeesBlack() {
        if (BoeBot.analogRead(rightLineSensorPin) > sensorTweak) {
            return true;
        } else {
            return false;
        }
    }

    private boolean centerSeesBlack() {
        if (BoeBot.analogRead(centralLineSensorPin) > sensorTweak) {
            return true;
        } else {
            return false;
        }
    }

    private void detectLine2Sensors() {
        //System.out.println(BoeBot.analogRead(1) + " Left");
        //System.out.println(BoeBot.analogRead(0) + " Right");
        if (!this.leftSeesBlack() && !this.rightSeesBlack()) {
            turning = false;
            System.out.println("On line");
            this.straightAhead();

        } else if (this.rightSeesBlack() && !this.leftSeesBlack()) {
            System.out.println("Left of line");
            if (!turning) {
                this.turnFullRight();
                turning = true;
            }

        } else if (this.leftSeesBlack() && !this.rightSeesBlack()) {
            System.out.println("Right of line");
            if (!turning) {
                this.turnFullLeft();
                turning = true;
            }

        } else {
            System.out.println("kruispunt");
        }
    }


    private void detectLine3Sensors() {
        if (!this.leftSeesBlack() && !this.rightSeesBlack() && this.centerSeesBlack()) {
            System.out.println("on line");
            turning = false;
            this.straightAhead();
        } else if (this.leftSeesBlack() && this.centerSeesBlack() && !this.rightSeesBlack()) {
            System.out.println("Slightly right of line");
            if (!turning) {
                this.turnSlightLeft();
                turning = true;
            }
        } else if (this.rightSeesBlack() && this.centerSeesBlack() && !this.leftSeesBlack()) {
            System.out.println("Slightly left of line");
            if (!turning) {
                this.turnSlightRight();
                turning = true;
            }
        } else if (this.leftSeesBlack() && !this.centerSeesBlack() && !this.rightSeesBlack()) {
            System.out.println("Right of Line");
            if (!turning) {
                this.turnFullLeft();
                turning = true;
            }
        } else if (this.rightSeesBlack() && !this.centerSeesBlack() && !this.leftSeesBlack()) {
            System.out.println("Left of line");
            if (!turning) {
                this.turnFullRight();
                turning = true;
            }
        } else {
            System.out.println("Kruispunt");
        }
    }

    @Override
    public void update(){
        if (this.centralLineSensorPin == -1) {
            this.detectLine2Sensors();
        } else {
            this.detectLine3Sensors();
        }

    }
}

