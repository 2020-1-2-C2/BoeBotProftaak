package Logic;

/**
 * Contains all the PinIds for other classes to use.
 */
public class Configuration {

    // GPIO pins
    static int infraredReceiverPinId = 0;
    static int ultraSonicReceiverTriggerPinId = 1;
    static int ultraSonicReceiverEchoPinId = 2;
    static int buzzerPinId = 3;
    static int servoMotor1PinId = 12;
    static int servoMotor2PinId = 13;

    // ADC pins
    static int lineFollowerLeftLineSensorADCPinId = 2;
    static int lineFollowerRightLineSensorADCPinId = 1;
    //TODO: implement the middle line sensor id.
    public static int lineFollowerMiddleLineSensorADCPinId = -1;
}
