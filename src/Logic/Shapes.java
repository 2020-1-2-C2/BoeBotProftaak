package Logic;

import TI.Timer;
import Utils.Updatable;

public class Shapes implements Updatable {
    private DriveSystem driveSystem;
    private Timer circleTimer;
    private Timer triangleTimer;
    private boolean circleTimerEnabled;
    private boolean triangleTimerEnabled;
    private int triangleCounter;
    private boolean triangleSegmentBoolean;

    /**
     * Enums to define which shape to drive.
     */
    public enum Shape {
        CIRCLE, TRIANGLE
    }

    /**
     * Constructor for Shapes class.
     * @param driveSystem DriveSystem object.
     */
    public Shapes(DriveSystem driveSystem) {
        this.driveSystem = driveSystem;
        this.circleTimer = new Timer(1000);
        this.triangleTimer = new Timer(1000);
        this.circleTimerEnabled = false;
        this.triangleCounter = 0;
        this.triangleSegmentBoolean = false;
    }

    /**
     * This method is a setup for the shape.
     * @param shape Shape enum/object
     */
    public void beginShape(Shape shape) {
        this.driveSystem.stop();
        this.driveSystem.setDirection(1);
        this.driveSystem.setSpeed(20);
        if (shape.equals(Shape.CIRCLE)) {
            this.circleTimerEnabled = true;
            //TODO: Time needs to be adjusted!
            this.circleTimer.setInterval(10000);
            circle();
        } else if (shape.equals(Shape.TRIANGLE)) {
            this.triangleTimerEnabled = true;
            this.triangleSegmentBoolean = false;
            triangle();
        }
    }

    /**
     * Drive in a circle
     */
    public void circle() {
        if (this.circleTimerEnabled) {
            this.driveSystem.turnLeft();
        } else {
            this.driveSystem.stop();
        }
    }

    /**
     * Drive in a triangle
     * TODO: Adjust timers
     */
    public void triangle() {
        if (this.triangleCounter < 3) {
            if (this.triangleSegmentBoolean) {
                //If true, corner
                this.driveSystem.stop();
                this.triangleTimer.setInterval(1600);
                this.triangleSegmentBoolean = false;
                this.driveSystem.turnLeft(80);
                this.triangleCounter++;
            } else {
                //If false, straight
                this.driveSystem.setSpeed(20);
                this.triangleTimer.setInterval(2000);
                this.triangleSegmentBoolean = true;
            }
        } else if (this.triangleCounter >= 3) {
            this.triangleTimerEnabled = false;
            this.triangleCounter = 0;
            this.driveSystem.stop();
        }
    }

    /**
     * Update method
     */
    @Override
    public void update() {
        if (this.circleTimerEnabled && this.circleTimer.timeout()) {
            this.circleTimerEnabled = false;
            circle();
        }

        if (this.triangleTimerEnabled && this.triangleTimer.timeout()) {
            //If true, corner
            this.triangleTimer.mark();
            triangle();
        }
    }
}