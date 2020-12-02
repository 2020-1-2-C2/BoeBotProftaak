import TI.BoeBot;
import Utils.Updatable;

import java.util.ArrayList;

public class TestMain {
    private ArrayList<Updatable> updatables = new ArrayList<>();

    public void Run() {
/*        Hardware.Motors motors = new Hardware.Motors();
        motors.drive(1300);
        updatables.add(motors);
        BoeBot.wait(1000);
        motors.goToSpeedRight(1700, 100);
        BoeBot.wait(1000);
        motors.goToSpeedLeft(1700, 100);*/

        while (true) {
            for (Updatable u : updatables) {
                u.update();
            }
            BoeBot.wait(10);
        }
    }

    public static void main(String[] args) {
        TestMain main = new TestMain();
        main.Run();
    }

/*    public static void main(String[] args) {
        Hardware.Motors motor = new Hardware.Motors();
        motor.drive(1700);
        BoeBot.wait(1000);
        motor.goToSpeed(1300,10);
        motor.emergencyBrake();
    }*/

/*    public static void main(String[] args) {
        Buzzer buzzer = new Buzzer(11);
        buzzer.buzz();
        buzzer.beep();
        buzzer.off();
    }*/
}
