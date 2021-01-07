package Utils;

//TODO: BE MORE SPECIFIC. Pretty please? <3
//TODO: Check if we missed classes that use this.
/**
 * Interface for updatable classes.
 * Used by all hardware components.
 * @see Hardware.BluetoothReceiver
 * @see Hardware.Buzzer
 * @see Hardware.DefaultLed
 * @see Hardware.DirectionalServo
 * @see Hardware.InfraredReceiver
 * @see Hardware.LineFollower
 * @see Hardware.NeoPixelLed
 * @see Hardware.RGBLed
 * @see Hardware.ServoMotor
 * @see Hardware.UltraSonicReceiver
 */
public interface Updatable {
    /**
     * TODO WRITE PLEASE
     */
    void update();
}
