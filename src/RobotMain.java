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

            System.out.println("test");
            System.out.println("Gamer");
            //Dit is een episch speler momentje

        }

        //testing if I can commit
        // testing testing testing Martijn
    }
}

