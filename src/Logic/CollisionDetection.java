package Logic;

import Hardware.UltraSonicReceiver;
import Utils.CollisionDetectionCallback;
import Utils.UltraSonicCallback;
import Utils.Updatable;
import java.util.Arrays;

/**
 * Class used to avoid collision with other objects. The BoeBot should never collide.
 * This class implements <a href="{@docRoot}/Utils/Updatable.html">Updatable</a> and <a href="{@docRoot}/Utils/UltraSonicCallback.html">UltraSonicCallback</a>.
 * This class uses <a href="{@docRoot}/Utils/CollisionDetectionCallback.html">CollisionDetectionCallback</a>.
 * @see Utils.Updatable
 * @see Utils.UltraSonicCallback
 * @see CollisionDetectionCallback
 * @version 1.0
 * @author Meindert Kempe, Berend de Groot, Martijn de Kam, Casper Lous, Lars Hoendervangers
 */
public class CollisionDetection implements Updatable, UltraSonicCallback {

    private CollisionDetectionCallback collisionDetectionCallback;
    private UltraSonicReceiver ultraSonicReceiver;

    private int counter = 0;
    private int[] distances = new int[9];

    /**
     * Constructor for <code>CollisionDetection</code>.
     * @param collisionDetectionCallback CollisionDetectionCallback object.
     */
    public CollisionDetection(CollisionDetectionCallback collisionDetectionCallback) {
        this.collisionDetectionCallback = collisionDetectionCallback;
        this.ultraSonicReceiver = new UltraSonicReceiver(Configuration.ultraSonicReceiverTriggerPinId, Configuration.ultraSonicReceiverEchoPinId, this);
    }

    /**
     * Overrides the <code>onUltraSonicPulse()</code> method in <a href="{@docRoot}/Utils/UltraSonicCallback.html">UltraSonicCallback</a>.
     * Receives the distance calculated using the ultrasonic-sensor pulse and prints it.
     * @param distance Calculated distance using an ultrasonic-sensor pulse.
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
        this.ultraSonicReceiver.update();
    }
}
