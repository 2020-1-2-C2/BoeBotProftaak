package hardware;

import TI.BoeBot;
import TI.Timer;
import utils.LineFollowCallback;
import utils.Updatable;

//TODO documentation for methods

/**
 * Class for the LineFollower hardware component, used by the BoeBot to navigate using lines.
 * @see utils.Updatable
 * @author Casper Lous, Berend de Groot, Lars Hoendervangers, Tom Martens, Meindert Kempe
 */
public class LineFollower implements Updatable {

    public enum LinePosition {
        NOT_ON_LINE,
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

    private Timer lineFollowerTimer = new Timer(20);

    private int sensorTweak;
    private boolean onWhite;

    public LineFollower(int leftLineSensorPin, int rightLineSensorPin, int middleLineSensorPin, LineFollowCallback lineFollowCallback) {
        this.lineFollowCallback = lineFollowCallback;
        this.leftLineSensorPin = leftLineSensorPin;
        this.rightLineSensorPin = rightLineSensorPin;
        this.centralLineSensorPin = middleLineSensorPin;
        this.sensorTweak = 1200;
        this.onWhite = false;
        this.lineFollowerTimer.mark();
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

    /**
     * Sends a callback saying where the robot is located relative to the line
     * Used when 2 line follower components are attached to breadboard
     */
    private void detectLine2Sensors() {
        if (!this.leftSeesBlack() && !this.rightSeesBlack()) {
            this.callBack = LinePosition.ON_LINE;

        } else if (this.rightSeesBlack() && !this.leftSeesBlack()) {
            this.callBack = LinePosition.LEFT_OF_LINE;

        } else if (this.leftSeesBlack() && !this.rightSeesBlack()) {
            this.callBack = LinePosition.RIGHT_OF_LINE;

        } else {
            this.callBack = LinePosition.CROSSING;
        }
    }

    /**
     * Sends a callback saying where the robot is located relative to the line
     * Used when 3 line follower components are attached to breadboard
     */
    private void detectLine3Sensors() {
        if (!this.leftSeesBlack() && !this.rightSeesBlack() && !this.centerSeesBlack()) {
//            System.out.println("Not on line");
            this.callBack = LinePosition.NOT_ON_LINE;
        } else if (!this.leftSeesBlack() && !this.rightSeesBlack() && this.centerSeesBlack()) {
//            System.out.println("On line");
            this.callBack = LinePosition.ON_LINE;
        } else if (this.leftSeesBlack() && this.centerSeesBlack() && !this.rightSeesBlack()) {
//            System.out.println("Slightly right of line");
            this.callBack = LinePosition.JUST_RIGHT_OF_LINE;
        } else if (this.rightSeesBlack() && this.centerSeesBlack() && !this.leftSeesBlack()) {
//            System.out.println("Slightly left of line");
            this.callBack = LinePosition.JUST_LEFT_OF_LINE;
        } else if (this.leftSeesBlack() && !this.centerSeesBlack() && !this.rightSeesBlack()) {
//            System.out.println("Right of Line");
            this.callBack = LinePosition.RIGHT_OF_LINE;
        } else if (this.rightSeesBlack() && !this.centerSeesBlack() && !this.leftSeesBlack()) {
//            System.out.println("Left of line");
            this.callBack = LinePosition.LEFT_OF_LINE;
        } else {
//            System.out.println("Kruispunt");
            this.callBack = LinePosition.CROSSING;
        }
    }


    /**
     * Used to calibrate the line followers. Sets sensorTweak to new value calculated from measurements
     */
    public void calibrate() {
        //black surface
        int blackCalibration = this.calibrateMeasurement();

        //white surface
        int whiteCalibration = 0;

        if (onWhite){
            whiteCalibration = this.calibrateMeasurement();
        }
        this.sensorTweak = (whiteCalibration + blackCalibration) / 2;
    }

    /**
     * Used by calibrate method, to acquire a measurement to be used for calibration.
     * This method is first called when the robot is positioned over a black surface, and next when over a white surface.
     * @return Average of 10 measurements by each line follower component
     */
    private int calibrateMeasurement() {
        int total = 0;
        for (int i = 0; i < 10; i++) {
            total = total + BoeBot.analogRead(this.leftLineSensorPin);
            if (this.centralLineSensorPin != -1) {
                total = total + BoeBot.analogRead(this.centralLineSensorPin);
            }
            total = total + BoeBot.analogRead(this.rightLineSensorPin);
            BoeBot.wait(1);
        }
        if (this.centralLineSensorPin != -1) {
            return total / 30;
        } else {
            return total / 20;
        }

    }

    /**
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * @see Updatable#update()
     */
    @Override
    public void update() {
        if (this.lineFollowerTimer.timeout()) {
            if (this.centralLineSensorPin == -1) {
                this.detectLine2Sensors();
            } else {
                this.detectLine3Sensors();
            }
        }

        if (this.callBack != null) {
            this.lineFollowCallback.onLineFollow(this.callBack);
        }
    }
}

