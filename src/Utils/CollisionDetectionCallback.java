package Utils;

/**
 * Interface to allow a callback on detecting an obstacle.
 * This is used by <a href="{@docRoot}/Logic/CollisionDetection.html">CollisionDetection.java</a>.
 * @version 1.0
 * @see Logic.CollisionDetection
 */
public interface CollisionDetectionCallback {
    /**
     * @param distance The distance from collision in cm.
     */
    void onCollisionDetection(int distance);
}
