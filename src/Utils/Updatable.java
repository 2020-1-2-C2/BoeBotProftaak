package Utils;

/**
 * Interface for updatable classes, allows for the use of the update method
 *
 * Hardware package classes that implement this interface:
 * @see Hardware.BluetoothReceiver
 * @see Hardware.Buzzer
 * @see Hardware.InfraredReceiver
 * @see Hardware.LineFollower
 * @see Hardware.NeoPixelLed
 * @see Hardware.ServoMotor
 * @see Hardware.UltraSonicReceiver
 *
 * Logic package classes that implement this interface:
 * @see Logic.BluetoothController
 * @see Logic.CollisionDetection
 * @see Logic.DriveSystem
 * @see Logic.InfraredController
 * @see Logic.LineFollowerController
 * @see Logic.NotificationSystemController
 * @see Logic.Shapes
 *
 * @author Berend de Groot, Lars Hoendervangers, Martijn de Kam
 * @version 1.0
 */
public interface Updatable {
    /**
     * update method usable by all classes that implement Updatable
     */
    void update();
}
