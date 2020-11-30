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

    }


    /**
     * Main run method for the boebot logic.
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
     * Receives the pressed button on an infrared remote and prints it.
     * @param button received button pressed on the infrared remote.
     */
    @Override
    public void OnInfraredButton(String button) {
        // TODO, code can be a null object with wrong or unrecognised measurements, this needs to be taken into account.
        System.out.println(button);
    }

    /**
     * Receives the distance calculated using the ultrasonicsensor pulse and prints it.
     * @param distance calculated distance using an ultrasonsicsensor pulse.
     */
    @Override
    public void onUltraSonicPulse(Integer distance) {
        // TODO, distance can be a null object with wrong measurements, this needs to be taken into account.
        System.out.println(distance);
    }
}