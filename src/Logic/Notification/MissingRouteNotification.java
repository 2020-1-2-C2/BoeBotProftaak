package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.Jingle;

import java.awt.*;
import java.util.ArrayList;

/**
 * Notification that plays whenever a route is missing in DriveSystem. Extends AbstractNotification.
 * @author Berend de Groot
 * @version 1.0
 * @see AbstractNotification#AbstractNotification(ArrayList, ArrayList)
 */
public class MissingRouteNotification extends AbstractNotification {
    /**
     * Constructor for the MissingRouteNotification class.
     * @param buzzers      Takes in an Arraylist of Buzzers to handle the sound.
     * @param neoPixelLeds Takes in an Arraylist of NeoPixelLeds to handle the lights.
     * @see Buzzer
     * @see NeoPixelLed
     */
    public MissingRouteNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        super(buzzers, neoPixelLeds);
        this.neoPixelLedColorA = Color.PINK;
        this.neoPixelLedColorB = Color.red;
        this.lightColorPattern = "ABABAB";
        for (NeoPixelLed neoPixelLed : neoPixelLeds){
            neoPixelLed.setBlinkingTimer(250);
        }
    }

    /**
     * Overrides notificationSpecificMethod() in AbstractNotification.java.
     * Abstract method all notifications have. This method contain instructions for the Buzzer and NeoPixelLeds on the BoeBot.
     * @see AbstractNotification#notificationSpecificMethod()
     */
    @Override
    public void notificationSpecificMethod() {
        System.out.println("MissingRouteNotification notificationSpecificMethod() has been called");
        int blinkTime = 250;

        //TODO: Add Jingle for missing route.
        this.getBuzzers().get(0).playSong(new Jingle().somebodyThatIUsedToKnow());

        for (int i = 0; i < 6; i++) {
            NeoPixelLed neoPixelLed = this.neoPixelLeds.get(i);

            if (this.lightColorPattern.charAt(i) == 'A') {
                neoPixelLed.on();
                neoPixelLed.setColor(this.neoPixelLedColorA);
                neoPixelLed.blink(blinkTime);
            } else if (this.lightColorPattern.charAt(i) == 'B') {
                neoPixelLed.on();
                neoPixelLed.setColor(this.neoPixelLedColorB);
                neoPixelLed.blink(blinkTime);
            }
        }
    }
}
