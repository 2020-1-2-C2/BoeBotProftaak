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
     * Callback on an object that implements the LineFollowCallback interface
     * is called upon when an object of LineFollower senses something.
     *
     * @param linePosition enumerator for different line positions
     */
    void onLineFollow(LineFollower.LinePosition linePosition);
}
