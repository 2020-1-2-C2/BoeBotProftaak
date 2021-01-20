package logic;

/**
 * Contains all the PinIds for other classes to use.
 * @author Martijn de Kam, Berend de Groot
 */
public class Configuration {

    // GPIO-pins
    static int infraredReceiverPinId = 0;
    static int ultraSonicReceiverTriggerPinId = 1;
    static int ultraSonicReceiverEchoPinId = 2;
    static int buzzerPinId = 3;
    static int servoMotor1PinId = 12;
    static int servoMotor2PinId = 13;

    // ADC-pins
    static int lineFollowerLeftLineSensorADCPinId = 3;
    static int lineFollowerRightLineSensorADCPinId = 1;
    static int lineFollowerMiddleLineSensorADCPinId = 2;

    // NeoPixelLeds
    public static int neoPixelLEDCount = 6;
}
