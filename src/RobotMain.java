import TI.BoeBot;
import TI.PinMode;

public class RobotMain {

    public static void main(String[] args) {

        boolean state = true;
        BoeBot.setMode(0, PinMode.Output);

        while (true) {
            state = !state;
            BoeBot.digitalWrite(0, state);
            BoeBot.wait(250);
        }
    }
}
