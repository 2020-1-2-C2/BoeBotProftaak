package Hardware;

import Logic.AudioPlaySystem;
import Logic.MusicNote;
import Logic.NotePitchGenerator;
import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import Utils.Updatable;

/**
 * <code>Buzzer</code> class, from which all buzzer sensors should have its own instance. <p>
 * The methods are used to play sounds on the <code>Buzzer</code>.
 * @see Utils.Updatable
 * @see Logic.NotePitchGenerator
 * @see Logic.AudioPlaySystem
 * @author Berend de Groot
 * @version 2.1
 */
public class Buzzer implements Updatable {
    private int pinId;
    private boolean isOn;

    private NotePitchGenerator notePitchGenerator = new NotePitchGenerator();

    private Timer noteDelayTimer;
    private int currentNoteCount;
    private AudioPlaySystem selectedSong;

    /**
     * Constructor for the buzzer hardware component, deriving from the <code>Buzzer</code> class.
     * @param pinId An integer representing the pin whom the buzzer is connected to.
     */
    public Buzzer(int pinId) {
        this.pinId = pinId;
        BoeBot.setMode(pinId, PinMode.Output);
        this.selectedSong = null;
        this.noteDelayTimer = new Timer(0); //Note: This is infinite.
        this.currentNoteCount = 0;
    }

    /**
     * Method that makes the Buzzer buzz for 1 second at 1000Hz.
     */
    public void buzz() {
        buzz(1000, 1000);
    }

    /**
     * Method that makes the <code>Buzzer</code> buzz at 1000Hz for the time specified in the parameter.
     * @param time Time in milliseconds. Buzzer stops buzzing after the timer has passed.
     */
    public void buzz(int time) {
        buzz(time, 1000);
    }

    /**
     * Makes the <code>Buzzer</code> buzz for a certain amount of time on a certain frequency.
     * @param time Time in milliseconds. Buzzer stops buzzing after the timer has passed.
     * @param freq Frequency in Hz.
     * @see BoeBot#freqOut(int, int, int)
     */
    public void buzz(int time, int freq) {
        BoeBot.freqOut(this.pinId, freq, time);
    }

    /**
     * Buzzes at a frequency similar to a piano note given as a parameter
     *
     * @param time   Time in milliseconds. Buzzer stops buzzing after the timer has passed.
     * @param note   Musical note without the octave (for example: C, G, A#).
     * @param octave Octave (for example: 4, 5).
     * @see Hardware.Buzzer#getNote(String, int)
     */
    public void buzz(int time, String note, int octave) {
        buzz(time, getNote(note, octave));
    }

    /**
     * Makes the <code>Buzzer</code> buzz for half a second (500 ms).
     * @see Buzzer#buzz()
     */
    public void beep() {
        buzz(500);
    }

    /**
     * Method used to turn the buzzer off. <p>
     * This will interrupt the <a href="{@docRoot}/Logic/Jingle.html">jingle</a>. It does this by setting <code>this.selectedSong</code> to <code>null</code>. <p>
     * If the user wants to play the <code>jingle</code> again he has to start over from the beginning by initiating it again.
     * @see Logic.Jingle
     */
    public void off() {
        this.selectedSong = null;
        this.isOn = false;
    }

    /**
     * Auto-generated getter.
     * @return Returns the isOn boolean (this.isOn).
     */
    public boolean getIsOn() {
        return this.isOn;
    }

    /**
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * @see Updatable#update()
     */
    @Override
    public void update() {
        if (this.selectedSong != null){
            if (this.currentNoteCount < this.selectedSong.getNotesToPlay().size()){
                if (this.noteDelayTimer.timeout()){
                    System.out.println("Went through TIMEOUT()");
                    buzz(this.selectedSong.getNotesToPlay().get(this.currentNoteCount).getNoteLengthInMS(),
                            this.selectedSong.getNotesToPlay().get(this.currentNoteCount).getNotePitch());
                    this.currentNoteCount++;
                        if (this.selectedSong.getNotesToPlay().size() - 1 > this.currentNoteCount){
                            this.noteDelayTimer.setInterval(this.selectedSong.getNotesToPlay().get(this.currentNoteCount).getNoteDelayInMS()
                                    + this.selectedSong.getNotesToPlay().get(this.currentNoteCount).getNoteLengthInMS());
                        }
                }
            } else {
                System.out.println("Went through ELSE");
                this.selectedSong = null;
                this.currentNoteCount = 0;
            }
        }
    }

    /**
     * Calculates the hertz for a musical note in <a href="{@docRoot}/Logic/NoteLengthGenerator.html">NotePitchGenerator</a>. <p>
     * All "A" notes, apart from A0, are as accurate as possible, with the bottleneck being the hardware (in this case, the buzzer).
     *
     * @param note A string containing a musical note (for example: B, A, G).
     * @param octave An int containing the octave the note should be played in. One octave is 12 halfsteps/semitones.
     * @return A rounded number containing the hz of the given musical note. This is calculated in an instance of the <a href="{@docRoot}/Logic/NoteLengthGenerator.html">NotePitchGenerator</a>.
     * @see NotePitchGenerator#getNote(String, int)
     */
    public int getNote(String note, int octave) {
        return this.notePitchGenerator.getNote(note, octave);
    }

    /**
     * Plays the song in the <a href="{@docRoot}/Logic/AudioPlaySystem.html">AudioPlaySystem</a> given as a parameter.
     * @param audioPlaySystem Method uses the notes from the <code>getNotesToPlay()</code> ArrayList.
     * @see AudioPlaySystem#getNotesToPlay()
     */
    public void playSong(AudioPlaySystem audioPlaySystem){
        this.selectedSong = audioPlaySystem;
        this.currentNoteCount = 0;
        this.noteDelayTimer.setInterval(1);
    }

    /**
     * Simple getter for the int <code>pinID</code> of this object.
     * @return this.pinId
     */
    public int getPinId() {
        return this.pinId;
    }

    /**
     * Method used for debugging purposes only. It prints out the pitch, length and the delay for a note.
     * @param musicNote Uses this to get information about the note.
     * @return String containing information about a musicNote.
     * @see MusicNote
     */
    private String toString(MusicNote musicNote) {
        return "NEW MUSICNOTE" + "\n"
                + "Note Pitch: " + musicNote.getNotePitch() + "\n"
                + "Note Length: " + musicNote.getNoteLength() + "\n"
                + "Note Delay: " + musicNote.getNoteDelay();
    }
}
