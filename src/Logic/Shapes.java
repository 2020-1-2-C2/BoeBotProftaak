package Logic;

import TI.Timer;
import Utils.Updatable;

public class Shapes implements Updatable {
    private DriveSystem driveSystem;
    private Timer circleTimer;
    private Timer triangleStraightTimer;
    private Timer triangleCornerTimer;
    private boolean timerEnabled;
    private int triangleCounter;

    public enum Shape {
        CIRCLE, TRIANGLE
    }

    public Shapes(DriveSystem driveSystem) {
        this.driveSystem = driveSystem;
        this.circleTimer = new Timer(1000);
        this.triangleCornerTimer = new Timer(1000);
        this.triangleStraightTimer = new Timer(1000);
        this.timerEnabled = false;
        this.triangleCounter = 0;
    }

    public void beginShape(Shape shape) {
        this.driveSystem.stop();
        this.driveSystem.setSpeed(20);
        if (shape.equals(Shape.CIRCLE)) {
            this.timerEnabled = true;
            //TODO: Time needs to be adjusted!
            this.circleTimer.setInterval(11000);
            circle();
        } else if (shape.equals(Shape.TRIANGLE)) {
            this.timerEnabled = true;
            triangle();
        }
    }

    public void circle() {
        if (this.timerEnabled) {
            this.driveSystem.turnLeft();
        } else {
            this.driveSystem.stop();
        }
    }

    public void triangle() {
        this.triangleCounter = 0;
        if (this.timerEnabled && this.triangleCounter % 2 == 0) {
            this.driveSystem.stop();
            System.out.println("Stop");
            this.driveSystem.setSpeed(10);
            System.out.println("Set speed");
            this.triangleStraightTimer.setInterval(5000);
            System.out.println("Set interval");
        } else if (this.timerEnabled && this.triangleCounter % 2 != 0) {
            this.driveSystem.stop();
            System.out.println("Stop");
            this.driveSystem.turnLeft();
            System.out.println("Turn");
            this.triangleCornerTimer.setInterval(2000);
            System.out.println("Set interval");
        }
    }

    @Override
    public void update() {
        if (this.timerEnabled && this.circleTimer.timeout()) {
            this.timerEnabled = false;
            circle();
        } else if (this.timerEnabled && this.triangleStraightTimer.timeout()) {
            triangle();
            this.triangleCounter++;
            this.triangleCornerTimer.mark();
            if (this.triangleCounter == 5) {
                this.timerEnabled = false;
            }
        } else if (this.timerEnabled && this.triangleCornerTimer.timeout()) {
            triangle();
            this.triangleCounter++;
            this.triangleStraightTimer.mark();
            if (this.triangleCounter == 5) {
                this.timerEnabled = false;
            }
        }
    }
}