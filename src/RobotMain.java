import TI.BoeBot;
import TI.PinMode;

public class RobotMain {

    public static void main(String[] args) {
        Motors motors = new Motors(1300);

        motors.drive();
        BoeBot.wait(2500);
        motors.goToSpeed(1700,100);
        motors.emergencyBrake();
    }
}

