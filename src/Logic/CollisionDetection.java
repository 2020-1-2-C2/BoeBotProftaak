package Logic;

import Utils.CollisionDetectionCallback;
import Utils.UltraSonicCallback;
import Utils.Updatable;

public class CollisionDetection implements Updatable, UltraSonicCallback {
    private CollisionDetectionCallback collisionDetectionCallback;

    private static final int STOPPING_DISTANCE = 20;
    private int stopCounter = 0;
    private int continueCounter = 0;

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

        if (distance < STOPPING_DISTANCE) {
            stopCounter++;
        } else {
            continueCounter++;
        }

        if (stopCounter + continueCounter > 10) {
            if (stopCounter > continueCounter) {

                collisionDetectionCallback.onCollisionDetection(distance);
                System.out.println(distance);
            }
            stopCounter = 0;
            continueCounter = 0;
        }
    }

    @Override
    public void update() {

    }
}
