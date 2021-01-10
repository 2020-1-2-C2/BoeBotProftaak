package Utils;

//TODO: BE MORE SPECIFIC. Pretty please? <3
//TODO: Check if we missed classes that use this.
/**
 * Interface for updatable classes used by all hardware components.
 * @see Hardware.BluetoothReceiver
 * @see Hardware.Buzzer
 * @see Hardware.DirectionalServo
 * @see Hardware.InfraredReceiver
 * @see Hardware.LineFollower
 * @see Hardware.NeoPixelLed
 * @see Hardware.ServoMotor
 * @see Hardware.UltraSonicReceiver
 * @author Berend de Groot, Lars Hoendervangers, Martijn de Kam
 * @version 1.0
 */
public interface Updatable {
    /**
     * TODO WRITE PLEASE
     */
    void update();
}
