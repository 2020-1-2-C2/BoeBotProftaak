import Hardware.InfraredReceiver;
import Hardware.UltraSonicReceiver;
import TI.BoeBot;
import TI.PinMode;
import Utils.InfraredCallback;
import Utils.UltraSonicCallback;
import Utils.Updatable;

import java.util.ArrayList;

public class RobotMain implements InfraredCallback, UltraSonicCallback {

    private ArrayList<Updatable> updatables = new ArrayList<>();

    public static void main(String[] args) {

        RobotMain main = new RobotMain();
        main.run();

//        Motors motors = new Motors();
//
//        motors.drive(1700);
//        BoeBot.wait(1000);
//        motors.goToSpeed(1300, 10);
//        motors.goToSpeedLeft(1700,20);
//        motors.goToSpeed(1500, 30);
//        motors.emergencyBrake();
    }


    /**
     * Main run methode voor de BoeBot logica.
     */
    public void run() {
        BoeBot.setMode(0, PinMode.Input);
        InfraredReceiver infraredReceiver = new InfraredReceiver(0, this);
        UltraSonicReceiver ultraSonicReceiver = new UltraSonicReceiver(1, 2, this);
        updatables.add(infraredReceiver);
        updatables.add(ultraSonicReceiver);

        while (true) {
            for (Updatable u : updatables) {
                u.update();
            }
            BoeBot.wait(10);
        }
    }

    /**
     * Ontvangt een infrarood-code en print deze.
     *
     * @param code
     */
    @Override
    public void OnInfraredCode(String code) {
        // TODO, code kan een null object zijn, dit gebeurt bij foutieve en niet herkende metingen. Dit moet worden verwerkt.
        System.out.println(code);
    }

    /**
     * Ontvangt de afstand van de ultrasoonsensor en print deze.
     * @param distance
     */
    @Override
    public void onUltraSonicPulse(Integer distance) {
        // TODO, distance kan een null object zijn, dit gebeurt bij foutieve metingen. Dit moet worden verwerkt.
        System.out.println(distance);
    }
}