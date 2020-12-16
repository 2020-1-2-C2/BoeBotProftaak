package Logic;

import Utils.CollisionDetectionCallback;
import Utils.UltraSonicCallback;
import Utils.Updatable;

import java.util.Arrays;

public class CollisionDetection implements Updatable, UltraSonicCallback {
    private CollisionDetectionCallback collisionDetectionCallback;

    private int stopCounter = 0;
    private int continueCounter = 0;

    private int counter = 0;
    private int[] distances = new int[9];

    /**
     * Constructor for CollisionDetection
     * @param collisionDetectionCallback CollisionDetectionCallback object
     */
    public CollisionDetection(CollisionDetectionCallback collisionDetectionCallback) {
        this.collisionDetectionCallback = collisionDetectionCallback;
    }

    /**
     * Receives the distance calculated using the ultrasonicsensor pulse and prints it.
     * @param distance calculated distance using an ultrasonsicsensor pulse.
     */
    @Override
    public void onUltraSonicPulse(Integer distance) {

        if (distance == null){
            distance = 0;
            System.out.println("Distance null object");
        }

        if (counter < distances.length) {
            distances[counter] = distance;
            counter++;
        } else {
            Arrays.sort(distances);
            collisionDetectionCallback.onCollisionDetection(distances[distances.length/2]);
            counter = 0;
        }

    }

    @Override
    public void update() {

    }
}
