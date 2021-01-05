package Logic;

import Utils.CollisionDetectionCallback;
import Utils.UltraSonicCallback;
import Utils.Updatable;

import java.util.Arrays;

/**
 * Class used to avoid collision with other objects. The BoeBot should never collide.
 * This class implements Updatable.java and UltraSonicCallback.java.
 * This class uses CollisionDetectionCallback.
 * @see Utils.Updatable
 * @see Utils.UltraSonicCallback
 * @see CollisionDetectionCallback
 * @version 1.0
 */
public class CollisionDetection implements Updatable, UltraSonicCallback {

    private CollisionDetectionCallback collisionDetectionCallback;

    private int counter = 0;
    private int[] distances = new int[9];

    /**
     * Constructor for CollisionDetection.
     *
     * @param collisionDetectionCallback CollisionDetectionCallback object
     */
    public CollisionDetection(CollisionDetectionCallback collisionDetectionCallback) {
        this.collisionDetectionCallback = collisionDetectionCallback;
    }

    /**
     * Overrides the onUltraSonicPulse() method in UltraSonicCallback.java
     * Receives the distance calculated using the ultrasonicsensor pulse and prints it.
     *
     * @param distance calculated distance using an ultrasonsicsensor pulse.
     * @see Utils.UltraSonicCallback
     */
    @Override
    public void onUltraSonicPulse(Integer distance) {
        if (distance == null) {
            distance = 0;
        }

        if (counter < distances.length) {
            distances[counter] = distance;
            counter++;
        } else {
            Arrays.sort(distances);
            collisionDetectionCallback.onCollisionDetection(distances[distances.length / 2]);
            counter = 0;
        }
    }

    /**
     * Empty update method.
     * @see Utils.Updatable
     */
    @Override
    public void update() {
    }
}
