import Hardware.Led;
import Hardware.Motors;
import Hardware.RGB;
import TI.BoeBot;
import Utils.Updatable;

import java.util.ArrayList;

public class RobotMain {

    private ArrayList<Updatable> updatables = new ArrayList<>();

    public void Run() {
        Led led = new Led(11);
        led.blink(1000);
        updatables.add(led);

        int i = 0;

        while (true) {
            if (i > 1000) {
                led.off();
                System.out.println("Hardware.Led off");
            } else {
                for (Updatable u : updatables) {
                    u.Update();
                }
            }
            i++;
            BoeBot.wait(10);
        }
    }

    public static void main(String[] args) {
        RobotMain main = new RobotMain();
        main.Run();
    }

/*    public static void main(String[] args) {
        Hardware.Motors motor = new Hardware.Motors();
        motor.drive(1700);
        BoeBot.wait(1000);
        motor.goToSpeed(200,10);
        motor.emergencyBrake();
    }*/
}