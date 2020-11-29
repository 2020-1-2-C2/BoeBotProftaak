import Hardware.InfraredReceiver;
import TI.BoeBot;
import TI.PinMode;
import Utils.InfraredCallback;
import Utils.Updatable;

import java.util.ArrayList;

public class RobotMain implements InfraredCallback {

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
        //infraredReceiver.addCallback(this);
        updatables.add(infraredReceiver);

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
        System.out.println(code);
    }
}