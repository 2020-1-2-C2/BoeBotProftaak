package Logic;

import Hardware.LineFollower;
import Utils.Updatable;

/**
 * Controller for the LineFollower class
 *
 * @author Martijn de Kam, Berend de Groot
 */
public class LineFollowerController implements Updatable {

    private LineFollower lineFollower;

    /**
     * Constructor for LineFollowerController
     * Creates a LineFollower object with 3 linefollow sensors
     *
     * @param driveSystem used for onLineFollow callbacks
     */
    public LineFollowerController(DriveSystem driveSystem) {
        this.lineFollower = new LineFollower(Configuration.lineFollowerLeftLineSensorADCPinId,
                Configuration.lineFollowerRightLineSensorADCPinId,
                Configuration.lineFollowerMiddleLineSensorADCPinId,
                driveSystem);
    }

    /**
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * Runs the <code>update()</code> method in <a href="{@docRoot}/Hardware/LineFollower.html">LineFollower</a>.
     * @see Updatable#update()
     * @see LineFollower#update()
     */

    public void calibrate() {
        this.lineFollower.calibrate();
    }

    @Override
    public void update() {
        this.lineFollower.update();
    }
}
