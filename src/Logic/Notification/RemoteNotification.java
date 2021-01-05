package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;

import java.util.ArrayList;

//TODO: Make class
public class RemoteNotification extends AbstractNotification{


    public RemoteNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
    }

    @Override
    public void notificationSpecificMethod() {

    }
}
