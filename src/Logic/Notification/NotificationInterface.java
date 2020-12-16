package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.NoteLengthGenerator;
import Logic.NotePitchGenerator;

import java.awt.*;
import java.util.ArrayList;

public interface NotificationInterface {

    void update();

    boolean isNotificationActive();

    void setNotificationActive(boolean notificationActive);

    void notificationSpecificMethod();

    ArrayList<Buzzer> getBuzzers();

    ArrayList<NeoPixelLed> getNeoPixelLeds();

}
