package Hardware;

import TI.BoeBot;
import Utils.LineFollowCallback;
import Utils.Updatable;

/**
 * Class for the LineFollower hardware component, used by the BoeBot to navigate using lines.
 *@see Utils.Updatable
 */
public class LineFollower implements Updatable {

    public enum LinePosition {
        LEFT_OF_LINE,
        JUST_LEFT_OF_LINE,
        RIGHT_OF_LINE,
        JUST_RIGHT_OF_LINE,
        ON_LINE,
        CROSSING

    }

    private boolean follow;
    private LineFollowCallback lineFollowCallback;

    private int leftLineSensorPin;
    private int rightLineSensorPin;
    private int centralLineSensorPin;
    private LinePosition callBack;
    private int speedDefault;

    private int sensorTweak;    //editid for calibration function please check
    private boolean turning = false;


    public LineFollower(int leftLineSensorPin, int rightLineSensorPin, LineFollowCallback lineFollowCallback) {
        this.lineFollowCallback = lineFollowCallback;
        this.leftLineSensorPin = leftLineSensorPin;
        this.rightLineSensorPin = rightLineSensorPin;
        this.sensorTweak = 1200; //editid for calibration function please check

        this.centralLineSensorPin = -1;

    }

    public LineFollower(int leftLineSensorPin, int rightLineSensorPin, int centralLineSensorPin, LineFollowCallback lineFollowCallback) {
        this(leftLineSensorPin, rightLineSensorPin, lineFollowCallback);
        this.centralLineSensorPin = centralLineSensorPin;
    }

    /*private void straightAhead() {
        servoLeft.update(STANDSTILL_SPEED - servoSpeedDefault);
        servoRight.update(STANDSTILL_SPEED + servoSpeedDefault );
    }

    private void turnFullRight() {
        servoRight.update(servoRight.getPulseWidth() + turningSpeed);
        servoLeft.update(servoLeft.getPulseWidth() + turningSpeed + turningTweak);
    }

    private void turnSlightRight() {
        servoRight.update(servoRight.getPulseWidth() + (turningSpeed / 3));
        servoLeft.update(STANDSTILL_SPEED);
    }

    private void turnFullLeft() {
        servoLeft.update(servoLeft.getPulseWidth() - turningSpeed);
        servoRight.update(servoRight.getPulseWidth() - turningSpeed - turningTweak);
    }

    private void turnSlightLeft() {
        servoRight.update(servoRight.getPulseWidth() - (turningSpeed / 3));
        servoLeft.update(STANDSTILL_SPEED);
    }*/

    public void followLine(boolean follow, int speed) {
        this.speedDefault = speed;
        this.follow = follow;
    }

    private boolean leftSeesBlack() {
        return BoeBot.analogRead(this.leftLineSensorPin) > this.sensorTweak;

    }

    private boolean rightSeesBlack() {
        return BoeBot.analogRead(this.rightLineSensorPin) > this.sensorTweak;
    }

    private boolean centerSeesBlack() {
        return BoeBot.analogRead(this.centralLineSensorPin) > this.sensorTweak;
    }

    private void detectLine2Sensors() {
/*        System.out.println(BoeBot.analogRead(0) + " Left");
        System.out.println(BoeBot.analogRead(1) + " Right");
        System.out.println(BoeBot.analogRead(2) + " Center");*/
        if (!this.leftSeesBlack() && !this.rightSeesBlack()) {
            this.callBack = LinePosition.ON_LINE;

        } else if (this.rightSeesBlack() && !this.leftSeesBlack()) {
            System.out.println("Left of line");
            this.callBack = LinePosition.LEFT_OF_LINE;

        } else if (this.leftSeesBlack() && !this.rightSeesBlack()) {
            System.out.println("Right of line");
            this.callBack = LinePosition.RIGHT_OF_LINE;

        } else {
            System.out.println("kruispunt");
            this.callBack = LinePosition.CROSSING;
        }
    }


    private void detectLine3Sensors() {
        if (!this.leftSeesBlack() && !this.rightSeesBlack() && this.centerSeesBlack()) {
            System.out.println("on line");
            this.callBack = LinePosition.ON_LINE;
        } else if (this.leftSeesBlack() && this.centerSeesBlack() && !this.rightSeesBlack()) {
            System.out.println("Slightly right of line");
            this.callBack = LinePosition.JUST_RIGHT_OF_LINE;
        } else if (this.rightSeesBlack() && this.centerSeesBlack() && !this.leftSeesBlack()) {
            System.out.println("Slightly left of line");
            this.callBack = LinePosition.JUST_LEFT_OF_LINE;
        } else if (this.leftSeesBlack() && !this.centerSeesBlack() && !this.rightSeesBlack()) {
            System.out.println("Right of Line");
            this.callBack = LinePosition.RIGHT_OF_LINE;
        } else if (this.rightSeesBlack() && !this.centerSeesBlack() && !this.leftSeesBlack()) {
            System.out.println("Left of line");
            this.callBack = LinePosition.LEFT_OF_LINE;
        } else {
            System.out.println("Kruispunt");
            this.callBack = LinePosition.CROSSING;
        }
    }

    //editid for calibration function please check
    public void calibrate() {
        this.sensorTweak = BoeBot.analogRead(this.centralLineSensorPin);
    }

    //editid for calibration function please check
    public void calibrate(int value) {
        this.sensorTweak = value;
    }

    /**
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * @see Utils.Updatable
     */
    @Override
    public void update() {
        if (this.centralLineSensorPin == -1) {
            this.detectLine2Sensors();
        } else {
            this.detectLine3Sensors();
        }

        this.lineFollowCallback.onLineFollow(this.callBack);
    }
}

