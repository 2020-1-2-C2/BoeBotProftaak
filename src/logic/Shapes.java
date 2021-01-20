package logic;

import TI.Timer;
import utils.TimerWithState;
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
    private TimerWithState circleTimer;
    private TimerWithState triangleTimer;
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
        this.circleTimer = new TimerWithState(1000, false);
        this.triangleTimer = new TimerWithState(1000, false);
        this.triangleCounter = 0;
        this.triangleSegmentBoolean = false;
    }

    /**
     * This method is a setup for the shape.
     * @param shape Shape enum/object
     */
    public void beginShape(Shape shape) {
        this.driveSystem.stop();
        this.driveSystem.setDirection(DriveSystem.FORWARD);
        this.driveSystem.setSpeed(20);
        if (shape.equals(Shape.CIRCLE)) {
            this.circleTimer.setOn(true);
            this.circleTimer.setInterval(10000);
            circle();
        } else if (shape.equals(Shape.TRIANGLE)) {
            this.triangleTimer.setOn(true);
            this.triangleSegmentBoolean = false;
            triangle();
        }
    }

    /**
     * Drive in a circle.
     */
    private void circle() {
        this.driveSystem.turnWithExistingSpeedAndTurnSpeed(DriveSystem.LEFT, DriveSystem.MIN_SPEED);
    }

    /**
     * Drive in a triangle.
     */
    private void triangle() {
        this.driveSystem.immediateStop();
        if (this.triangleCounter < 3) {
            if (this.triangleSegmentBoolean) {
                this.triangleTimer.setInterval(4500);
                this.triangleSegmentBoolean = false;
                this.driveSystem.turnLeft();
            } else {
                this.driveSystem.setSpeed(20);
                this.triangleTimer.setInterval(3000);
                this.triangleSegmentBoolean = true;
                this.triangleCounter++;
            }
        } else {
            this.triangleTimer.setOn(false);
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
        if (this.circleTimer.timeout()) {
            this.circleTimer.setOn(false);
            this.driveSystem.stop();
        }

        if (this.triangleTimer.timeout()) {
            this.triangleTimer.mark();
            this.triangle();
        }
    }
}
