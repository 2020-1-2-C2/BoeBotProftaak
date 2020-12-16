package Utils;

public interface CollisionDetectionCallback {
    /**
     * @param distance distance from collision in cm
     */
    void onCollisionDetection(int distance);
}
