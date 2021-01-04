package Logic.Notification;

import Hardware.Buzzer;
import Hardware.NeoPixelLed;
import Logic.NoteLengthGenerator;
import Logic.NotePitchGenerator;
import Utils.Updatable;

import java.awt.*;
import java.util.ArrayList;

//TODO: Replace the update() method, since usage of update() like this is redundant.

public abstract class AbstractNotification implements Updatable {

    //TODO: Check access-modifiers
    protected ArrayList<Buzzer> buzzers;
    protected ArrayList<NeoPixelLed> neoPixelLeds;
    protected NotePitchGenerator notePitchGenerator = new NotePitchGenerator();
    protected NoteLengthGenerator noteLengthGenerator = new NoteLengthGenerator(128);
    protected Color neoPixelLedColorA;
    protected Color neoPixelLedColorB;
    protected String lightColorPattern;

    public AbstractNotification(ArrayList<Buzzer> buzzers, ArrayList<NeoPixelLed> neoPixelLeds) {
        this.buzzers = buzzers;
        this.neoPixelLeds = neoPixelLeds;
    }

    public abstract void notificationSpecificMethod();

    /**
     * Creates an instance of EmptyNotification
     */
    public void cancel() {
        this.buzzers.get(0).off();
        for (NeoPixelLed neoPixelLed : neoPixelLeds){
            neoPixelLed.off();
        }
        new EmptyNotification(this.buzzers, this.neoPixelLeds);
    }

    public ArrayList<Buzzer> getBuzzers() {
        return this.buzzers;
    }

    public ArrayList<NeoPixelLed> getNeoPixelLeds() {
        return this.neoPixelLeds;
    }

    //TODO: Delete when new system is fully operational
//    public void ABXcode() {
//        int blinkTime = 250;
//
//        for (int i = 0; i < 6; i++) {
//            NeoPixelLed neoPixelLed = this.neoPixelLeds.get(i);
//
//            if (this.lightColorPattern.charAt(i) == 'A') {
//                neoPixelLed.on();
//                neoPixelLed.setColor(neoPixelLedColorA);
//                neoPixelLed.blink(blinkTime);
//            } else if (this.lightColorPattern.charAt(i) == 'B') {
//                neoPixelLed.on();
//                neoPixelLed.setColor(neoPixelLedColorB);
//                neoPixelLed.blink(blinkTime);
//            } else if (this.lightColorPattern.charAt(i) == 'X') { //TODO: Check whether this messes with the other on/off code.
//                neoPixelLed.off();
//            }
//        }
//    }
}
