package Logic;

/**
 * Contains all the PinIds for other classes to use.
 * @author Martijn de Kam, Berend de Groot
 */
public class Configuration {

    // GPIO pins
    public static int infraredReceiverPinId = 0;
    public static int ultraSonicReceiverTriggerPinId = 1;
    public static int ultraSonicReceiverEchoPinId = 2;
    public static int buzzerPinId = 3;
    public static int servoMotor1PinId = 12;
    public static int servoMotor2PinId = 13;

    // ADC pins
    public static int lineFollowerLeftLineSensorADCPinId = 3;
    public static int lineFollowerRightLineSensorADCPinId = 1;
    public static int lineFollowerMiddleLineSensorADCPinId = 2;
}
