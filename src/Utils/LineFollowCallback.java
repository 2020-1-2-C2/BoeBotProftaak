package Utils;

import Hardware.LineFollower;

public interface LineFollowCallback {
    void onLineFollow(LineFollower.LinePosition linePosition);
}
