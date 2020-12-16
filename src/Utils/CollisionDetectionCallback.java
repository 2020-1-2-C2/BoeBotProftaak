package Utils;

/**
 * Interface to allow a callback on detecting an obstacle
 */
public interface CollisionDetectionCallback {
    /**
     * @param distance distance from collision in cm
     */
    void onCollisionDetection(int distance);
}
