package Utils;

/**
 * Interface to allow a callback on detecting an obstacle.
 * This is used by <a href="{@docRoot}/Logic/CollisionDetection.html">CollisionDetection</a>.
 * @version 1.0
 * @see Logic.CollisionDetection
 * @author Meindert Kempe, Casper Lous, Berend de Groot, Martijn de Kam
 */
public interface CollisionDetectionCallback {
    /**
     * @param distance The distance from collision in cm.
     */
    void onCollisionDetection(int distance);
}
