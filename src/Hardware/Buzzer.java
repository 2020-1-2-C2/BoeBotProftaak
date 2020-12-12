package Hardware;

import TI.BoeBot;
import Utils.Updatable;

public class Buzzer implements Updatable {
    private int pinId;
    private boolean isOn;

    /**
     *
     * @param pinId is the pin where the buzzer is connected to.
     */
    public Buzzer(int pinId) {
        this.pinId = pinId;
    }

    /**
     * Buzz for 1 second on 1000Hz.
     */
    public void buzz() {
        buzz(1000,1000);
    }

    /**
     * Buzz for a certain amount of time on 1000Hz.
     * @param time in milliseconds.
     */
    public void buzz(int time) {
        buzz(time,1000);
    }

    /**
     * Buzz for a certain amount of time on a certain frequency.
     * @param time in milliseconds.
     * @param freq in Hz.
     */
    public void buzz(int time, int freq) {
        BoeBot.freqOut(this.pinId, freq, time);
    }

    /**
     * Buzzes at a frequency similar to a piano note given as a parameter
     * @param time in milliseconds.
     * @param note musical note without the octave (for example: C, G, A#).
     * @param octave octave (for example: 4, 5).
     */
    public void buzz(int time, String note, int octave){
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
     * @param note A string containing a musical note (for example: B3, A6, G5).
     * @return A rounded number containing the hz of the given musical note.
     */
    public int getNote(String note, int octave){
        double noteFrequency;
        int noteOctave = octave + 1;

        if (note.contains("C")){
            noteFrequency = 16.35 * noteOctave;
        } else if (note.contains("C#")){
            noteFrequency = 17.32 * noteOctave;
        } else if (note.contains("D")){
            noteFrequency = 18.35 * noteOctave;
        } else if (note.contains("D#")){
            noteFrequency = 19.44 * noteOctave;
        } else if (note.contains("E")){
            noteFrequency = 20.60 * noteOctave;
        } else if (note.contains("F")){
            noteFrequency = 21.82 * noteOctave;
        } else if (note.contains("F#")){
            noteFrequency = 23.12 * noteOctave;
        } else if (note.contains("G")){
            noteFrequency = 24.49 * noteOctave;
        } else if (note.contains("G#")){
            noteFrequency = 25.95 * noteOctave;
        } else if (note.contains("A")){
            noteFrequency = 27.50 * noteOctave;
        } else if (note.contains("A#")){
            noteFrequency = 29.13 * noteOctave;
        } else if (note.contains("B")){
            noteFrequency = 30.86 * noteOctave;
        } else {
            noteFrequency = 440.0;
        }

        return Math.round((float)noteFrequency);
    }

}
