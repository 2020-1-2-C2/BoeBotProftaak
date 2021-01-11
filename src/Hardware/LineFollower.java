package Hardware;

import TI.BoeBot;
import Utils.LineFollowCallback;
import Utils.Updatable;

//TODO documentation for methods

/**
 * Class for the LineFollower hardware component, used by the BoeBot to navigate using lines.
 *@see Utils.Updatable
 * @author Casper Lous, Berend de Groot, Lars Hoendervangers, Tom Martens, Meindert Kempe
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

    private LineFollowCallback lineFollowCallback;

    private int leftLineSensorPin;
    private int rightLineSensorPin;
    private int centralLineSensorPin;
    private LinePosition callBack;

    private int sensorTweak;    //editid for calibration function please check
    private boolean onWhite = false;


    public LineFollower(int leftLineSensorPin, int rightLineSensorPin, LineFollowCallback lineFollowCallback) {
        this(leftLineSensorPin, rightLineSensorPin, -1, lineFollowCallback);
    }

    public LineFollower(int leftLineSensorPin, int rightLineSensorPin, int middleLineSensorPin, LineFollowCallback lineFollowCallback) {
        this.lineFollowCallback = lineFollowCallback;
        this.leftLineSensorPin = leftLineSensorPin;
        this.rightLineSensorPin = rightLineSensorPin;
        this.centralLineSensorPin = middleLineSensorPin;
        this.sensorTweak = 1200; //editid for calibration function please check
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
        //black surface
        int blackCalibration = this.calibrateMeasurement();

        //white surface
        int whiteCalibration = 0;

        //TODO: find reliable way to determine if bot is on white surface, likely through user input
        if (onWhite){
            whiteCalibration = this.calibrateMeasurement();
        }
        this.sensorTweak = (whiteCalibration + blackCalibration) / 2;
    }

    //editid for calibration function please check
    public void calibrate(int value) {
        this.sensorTweak = value;
    }

    private int calibrateMeasurement(){
        int total = 0;
        for (int i = 0; i < 10; i++){
            total = total + BoeBot.analogRead(this.leftLineSensorPin);
            total = total + BoeBot.analogRead(this.centralLineSensorPin);
            total = total + BoeBot.analogRead(this.rightLineSensorPin);
            BoeBot.wait(1);
        }
        return total / 30;
    }

    /**
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * @see Updatable#update()
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

