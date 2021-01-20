package hardware;

import TI.BoeBot;
import TI.Timer;
import utils.LineFollowCallback;
import utils.Updatable;

/**
 * Class for the LineFollower hardware component, used by the BoeBot to navigate using lines.
 * @see utils.Updatable
 * @author Casper Lous, Berend de Groot, Tom Martens, Meindert Kempe
 */
public class LineFollower implements Updatable {

    /**
     * Enumerations for the LineFollower class.
     */
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
    private Timer lineFollowerTimer = new Timer(100);
    private int sensorTweak;
    private boolean onWhite;

    /**
     * Constructor for the LineFollower class.
     */
    public LineFollower(int leftLineSensorPin, int rightLineSensorPin, int middleLineSensorPin, LineFollowCallback lineFollowCallback) {
        this.lineFollowCallback = lineFollowCallback;
        this.leftLineSensorPin = leftLineSensorPin;
        this.rightLineSensorPin = rightLineSensorPin;
        this.centralLineSensorPin = middleLineSensorPin;
        this.sensorTweak = 1200;
        this.onWhite = false;
        this.lineFollowerTimer.mark();
    }

    /**
     * This method returns true when the left line follower sensor sees black.
     */
    private boolean leftSeesBlack() {
        return BoeBot.analogRead(this.leftLineSensorPin) > this.sensorTweak;
    }

    /**
     * This method returns true when the right line follower sensor sees black.
     */
    private boolean rightSeesBlack() {
        return BoeBot.analogRead(this.rightLineSensorPin) > this.sensorTweak;
    }

    /**
     * This method returns true when the center line follower sensor sees black.
     */
    private boolean centerSeesBlack() {
        return BoeBot.analogRead(this.centralLineSensorPin) > this.sensorTweak;
    }

    /**
     * Sends a callback saying where the robot is located relative to the line.
     * Is used when 2 line follower components are attached to breadboard.
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
     * Saves a callback saying where the robot is located relative to the line.
     * Used when 3 line follower components are attached to breadboard.
     */
    private void detectLine3Sensors() {
        boolean leftSeesBlack = leftSeesBlack();
        boolean rightSeesBlack = rightSeesBlack();
        boolean centerSeesBlack = centerSeesBlack();

        if (!leftSeesBlack && !rightSeesBlack && !centerSeesBlack) {
            this.callBack = LinePosition.NOT_ON_LINE;
        } else if (!leftSeesBlack && !rightSeesBlack && centerSeesBlack) {
            this.callBack = LinePosition.ON_LINE;
        } else if (leftSeesBlack && centerSeesBlack && !rightSeesBlack) {
            this.callBack = LinePosition.JUST_RIGHT_OF_LINE;
        } else if (rightSeesBlack && centerSeesBlack && !leftSeesBlack) {
            this.callBack = LinePosition.JUST_LEFT_OF_LINE;
        } else if (leftSeesBlack && !centerSeesBlack && !rightSeesBlack) {
            this.callBack = LinePosition.RIGHT_OF_LINE;
        } else if (rightSeesBlack && !centerSeesBlack && !leftSeesBlack) {
            this.callBack = LinePosition.LEFT_OF_LINE;
        } else {
            this.callBack = LinePosition.CROSSING;
        }
    }


    /**
     * Used to calibrate the line followers. Sets sensorTweak to new value calculated from measurements.
     */
    public void calibrate() {
        //Black surface.
        int blackCalibration = this.calibrateMeasurement();

        //wWite surface.
        int whiteCalibration = 0;

        if (this.onWhite) {
            whiteCalibration = this.calibrateMeasurement();
        }
        this.sensorTweak = (whiteCalibration + blackCalibration) / 2;
    }

    /**
     * Used by calibrate method, to acquire a measurement to be used for calibration.
     * This method is first called when the robot is positioned over a black surface, and next when over a white surface.
     * @return Average of 10 measurements by each line follower component.
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
            this.lineFollowCallback.onLineFollow(this.callBack);
        }
    }
}

