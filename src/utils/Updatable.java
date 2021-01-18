package utils;

/**
 * Interface for updatable classes, allows for the use of the update method
 *
 * hardware package classes that implement this interface:
 * @see hardware.BluetoothReceiver
 * @see hardware.Buzzer
 * @see hardware.InfraredReceiver
 * @see hardware.LineFollower
 * @see hardware.NeoPixelLed
 * @see hardware.ServoMotor
 * @see hardware.UltraSonicReceiver
 *
 * logic package classes that implement this interface:
 * @see logic.BluetoothController
 * @see logic.CollisionDetection
 * @see logic.DriveSystem
 * @see logic.InfraredController
 * @see logic.LineFollowerController
 * @see logic.NotificationSystemController
 * @see logic.Shapes
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
