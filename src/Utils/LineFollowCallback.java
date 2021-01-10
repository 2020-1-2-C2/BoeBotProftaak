package Utils;

import Hardware.LineFollower;

/**
 * Callback for the LineFollower class.
 * This interface contains one method.
 * @see LineFollower
 * @version 1.0
 * @author Casper Lous, Berend de Groot
 */
public interface LineFollowCallback {
    /**
     * TODO WRITE PLEASE
     * @param linePosition TODO WRITE PLEASE
     */
    void onLineFollow(LineFollower.LinePosition linePosition);
}
