package Logic;

/**
 * Instances of <code>MusicNote</code> are used by <a href="{@docRoot}/Logic/AudioPlaySystem.html">AudioPlaySystem</a>
 * to store note information when playing <a href="{@docRoot}/Logic/Jingle.html">Jingle</a>s.
 * @author Berend de Groot
 * @version 1.2
 */
public class MusicNote {

    private double noteLength;
    private int notePitch;
    private double noteDelay;

    /**
     * Constructor that creates an object of this class.
     * @param noteLength Length of the note (Should be an attribute of an instance of <a href="{@docRoot}/Logic/NoteLengthGenerator.html">NoteLengthGenerator</a>)..
     * @param notePitch Pitch of the note (for example: C4, A#6) (Should be a value generated with <a href="{@docRoot}/Logic/NoteLengthGenerator.html">NotePitchGenerator</a>).
     * @param noteDelay Timing; this is the time it waits after the note before this note is done playing.
     */
    MusicNote(double noteLength, int notePitch, double noteDelay) {
        this.noteLength = noteLength;
        this.notePitch = notePitch;
        this.noteDelay = noteDelay;
    }

    /**
     * Constructor that creates an object of this class.
     * @param noteLength Length of the note (Should be an attribute of an instance of <a href="{@docRoot}/Logic/NoteLengthGenerator.html">NoteLengthGenerator</a>)..
     * @param notePitch Pitch of the note (for example: C4, A#6) (Should be a value generated with <a href="{@docRoot}/Logic/NoteLengthGenerator.html">NotePitchGenerator</a>).
     */
    MusicNote(double noteLength, int notePitch) {
        this.noteLength = noteLength;
        this.notePitch = notePitch;
    }

    /**
     * Auto-generated getter for the <code>noteLength</code> attribute.
     * @return this.noteLength
     */
    public double getNoteLength() {
        return this.noteLength;
    }

    /**
     * Getter for the <code>noteLength</code> attribute, used by the <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a>.
     * This is because the <code>Buzzer</code> uses an integer (representing MS) for the timing.
     * @return noteLength * 1000 as an integer (so decimals might be lost).
     */
    public int getNoteLengthInMS() {
        return ((int) (this.getNoteLength() * 1000));
    }

    /**
     * Auto-generated getter for the <code>noteDelay</code> attribute.
     * @return this.noteDelay.
     */
    public double getNoteDelay() {
        return this.noteDelay;
    }

    /**
     * Getter for the <code>noteDelay</code> attribute in MS, used by the <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a>.
     * @return this.noteDelay * 1000 as an integer.
     */
    public int getNoteDelayInMS() {
        return ((int) (this.noteDelay * 1000));
    }

    /**
     * Auto-generated getter for the <code>notePitch</code> attribute.
     * @return The pitch of the note (this.notePitch).
     */
    public int getNotePitch() {
        return this.notePitch;
    }
}
