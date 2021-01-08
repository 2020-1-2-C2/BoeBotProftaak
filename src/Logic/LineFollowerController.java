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

    @Override
    public void update() {
        this.lineFollower.update();
    }
}
