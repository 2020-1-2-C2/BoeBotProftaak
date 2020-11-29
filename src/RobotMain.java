import TI.BoeBot;
import TI.PinMode;

public class RobotMain {

    public static void main(String[] args) {
        Motors motors = new Motors();

/*        boolean state = true;
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
        */

        motors.drive(1700);
        BoeBot.wait(1000);
        motors.goToSpeed(1300, 10);
        motors.goToSpeedLeft(1700,20);
        motors.goToSpeed(1500, 30);
        motors.emergencyBrake();
    }
}

