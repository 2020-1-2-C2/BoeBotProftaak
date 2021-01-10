package Logic;

import Hardware.UltraSonicReceiver;
import Utils.CollisionDetectionCallback;
import Utils.UltraSonicCallback;
import Utils.Updatable;

import java.util.Arrays;

/**
 * Class used to avoid collision with other objects. The BoeBot should never collide.
 * This class implements <a href="{@docRoot}/Utils/Updatable.html">Updatable.java</a> and <a href="{@docRoot}/Utils/UltraSonicCallback.html">UltraSonicCallback.java</a>.
 * This class uses <a href="{@docRoot}/Utils/CollisionDetectionCallback.html">CollisionDetectionCallback</a>.
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
     * Constructor for <code>CollisionDetection</code>.
     * @param collisionDetectionCallback CollisionDetectionCallback object
     */
    public CollisionDetection(CollisionDetectionCallback collisionDetectionCallback) {
        this.collisionDetectionCallback = collisionDetectionCallback;
        //TODO: Should the UltroSonicReceiver be used?! Why is this never used?! Remove it if unnecessary.
        UltraSonicReceiver ultraSonicReceiver = new UltraSonicReceiver(Configuration.ultraSonicReceiverTriggerPinId, Configuration.ultraSonicReceiverEchoPinId, this);
    }

    /**
     * Overrides the <code>onUltraSonicPulse()</code> method in <a href="{@docRoot}/Utils/UltraSonicCallback.html">UltraSonicCallback.java</a>.
     * Receives the distance calculated using the ultrasonicsensor pulse and prints it.
     * @param distance Calculated distance using an ultrasonsicsensor pulse.
     * @see Utils.UltraSonicCallback
     */
    @Override
    public void onUltraSonicPulse(Integer distance) {
        if (distance == null) {
            distance = 0;
        }

        if (this.counter < this.distances.length) {
            this.distances[this.counter] = distance;
            this.counter++;
        } else {
            Arrays.sort(this.distances);
            this.collisionDetectionCallback.onCollisionDetection(this.distances[this.distances.length / 2]);
            this.counter = 0;
        }
    }

    /**
     * Empty <code>update()</code> method.
     * @see Updatable#update()
     */
    @Override
    public void update() {
    }
}
