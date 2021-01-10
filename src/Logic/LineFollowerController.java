package Logic;

import Hardware.LineFollower;
import Utils.Updatable;

public class LineFollowerController implements Updatable {

    private LineFollower lineFollower;

    public LineFollowerController(DriveSystem driveSystem) {
        this.lineFollower = new LineFollower(Configuration.lineFollowerLeftLineSensorADCPinId,
                Configuration.lineFollowerRightLineSensorADCPinId,
                driveSystem);
    }

    /**
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * Runs the <ocde>update()</code> method in <a href="{@docRoot}/Hardware/LineFollower.html">LineFollower</a>.
     * @see Updatable#update()
     * @see LineFollower#update()
     */
    @Override
    public void update() {
        this.lineFollower.update();
    }
}
