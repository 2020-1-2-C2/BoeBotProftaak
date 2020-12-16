package Hardware;

import Logic.AudioPlaySystem;
import Logic.MusicNote;
import Logic.NotePitchGenerator;
import TI.BoeBot;
import TI.PinMode;
import Utils.Updatable;

/**
 * Buzzer class, from which all buzzer sensors should have its own instance. The methods are used to play sounds on the Buzzer.
 */
public class Buzzer implements Updatable {
    private int pinId;
    private boolean isOn;

    private NotePitchGenerator notePitchGenerator = new NotePitchGenerator();

    /**
     * Constructor for the buzzer sensor, deriving from the Buzzer.java class.
     *
     * @param pinId An integer representing the pin whom the buzzer is connected to.
     */
    public Buzzer(int pinId) {
        this.pinId = pinId;
        BoeBot.setMode(pinId, PinMode.Output);
    }

    /**
     * Buzz for 1 second on 1000Hz.
     */
    public void buzz() {
        buzz(1000, 1000);
    }

    /**
     * Buzz for a certain amount of time on 1000Hz.
     *
     * @param time in milliseconds.
     */
    public void buzz(int time) {
        buzz(time, 1000);
    }

    /**
     * Buzz for a certain amount of time on a certain frequency.
     *
     * @param time in milliseconds.
     * @param freq in Hz.
     */
    public void buzz(int time, int freq) {
        BoeBot.freqOut(this.pinId, freq, time);
    }

    /**
     * Buzzes at a frequency similar to a piano note given as a parameter
     *
     * @param time   in milliseconds.
     * @param note   musical note without the octave (for example: C, G, A#).
     * @param octave octave (for example: 4, 5).
     */
    public void buzz(int time, String note, int octave) {
        buzz(time, getNote(note, octave));
    }

    /**
     * Buzz for half a second.
     */
    public void beep() {
        buzz(500);
    }

    /**
     * Turns the buzzer off. (If the buzzer won't shut off automatically.)
     */
    public void off() {
        this.isOn = false;
    }

    /**
     * @return the isOn boolean.
     */
    public boolean getIsOn() {
        return this.isOn;
    }

    @Override
    public void update() {
    }

    /**
     * Data collected from https://nl.wikipedia.org/wiki/Toonhoogtetabel
     * All "A" notes, apart from A0, are as accurate as possible, with the bottleneck being the hardware (in this case, the buzzer).
     *
     * @param note A string containing a musical note (for example: B3, A6, G5).
     * @return A rounded number containing the hz of the given musical note.
     */
    public int getNote(String note, int octave) {
        return notePitchGenerator.getNote(note, octave);
    }

    /**
     * Plays the song in the audioPlaySystem given as a parameter.
     *
     * @param audioPlaySystem
     */
    public void playSong(AudioPlaySystem audioPlaySystem) {
        System.out.println("Playing song");
        for (MusicNote musicNote : audioPlaySystem.getNotesToPlay()) {
            BoeBot.wait(musicNote.getNoteDelayInMS());
            System.out.println(musicNote.getNoteDelayInMS());
            buzz(musicNote.getNoteLengthInMS(), musicNote.getNotePitch());
        }
    }


}
