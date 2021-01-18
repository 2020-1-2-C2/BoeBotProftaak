package logic;

import TI.Timer;
import utils.Updatable;

/**
 * Class containing the instructions for the BoeBot when it receives the command to drive in a shape defined in this class.
 * This class implements <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
 * @version 1.0
 * @since 2.0
 * @see Updatable#update()
 * @see DriveSystem
 * @author Meindert Kempe, Berend de Groot, Lars Hoendervangers, Tom Martens
 */
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
     * This can either be a circle or a triangle.
     */
    public enum Shape {
        CIRCLE, TRIANGLE
    }
    /**
     * Constructor for <code>Shapes</code> class.
     * @param driveSystem <code>DriveSystem</code> object.
     * @see DriveSystem
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
            this.circleTimer.setInterval(20000);
            circle();
        } else if (shape.equals(Shape.TRIANGLE)) {
            this.triangleTimerEnabled = true;
            this.triangleSegmentBoolean = false;
            triangle();
        }
    }

    /**
     * Drives in a circle.
     */
    private void circle() {
        if (this.circleTimerEnabled) {
            this.driveSystem.turnLeft();
        } else {
            this.driveSystem.stop();
        }
    }

    /**
     * Drives in a triangle.
     */
    private void triangle() {
        if (this.triangleCounter < 4) {
            if (this.triangleSegmentBoolean) {
                this.driveSystem.stop();
                this.triangleTimer.setInterval(1350);
                this.triangleSegmentBoolean = false;
                this.driveSystem.turn(DriveSystem.LEFT, 50);
                this.triangleCounter++;
            } else {
                this.driveSystem.setSpeed(20);
                this.triangleTimer.setInterval(2000);
                this.triangleSegmentBoolean = true;
            }
        } else {
            this.triangleTimerEnabled = false;
            this.triangleCounter = 0;
            this.driveSystem.stop();
        }
    }

    /**
     * Checks whether the timer for one of the <code>Shapes</code> has <code>timeout()</code>. If the times has timed-out it'll drive the shape accordingly.
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * @see Updatable#update()
     */
    @Override
    public void update() {
        if (this.circleTimerEnabled && this.circleTimer.timeout()) {
            this.circleTimerEnabled = false;
            this.circle();
        }

        if (this.triangleTimerEnabled && this.triangleTimer.timeout()) {
            this.triangleTimer.mark();
            this.triangle();
        }
    }
}
