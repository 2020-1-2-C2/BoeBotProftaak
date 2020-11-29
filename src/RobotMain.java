import TI.BoeBot;
import TI.PinMode;

public class RobotMain {

    public static void main(String[] args) {
        Motors motors = new Motors(1300);

        boolean state = true;
        BoeBot.setMode(0, PinMode.Output);

        while (true) {
            state = !state;
            BoeBot.digitalWrite(0, state);
            BoeBot.wait(250);

            System.out.println("test");
            System.out.println("Gamer");
            //Dit is een episch speler momentje
            System.out.println("Martijn");

        }


        motors.drive();
        BoeBot.wait(2500);
        motors.goToSpeed(1700,100);
        motors.emergencyBrake();
    }
}

