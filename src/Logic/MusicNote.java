package Logic;

//TODO: Update all the documentation.

/**
 * Instances of <code>MusicNote</code> are used by <a href="{@docRoot}/Logic/AudioPlaySystem.html">AudioPlaySystem</a>
 * to store note information when playing <a href="{@docRoot}/Logic/Jingle.html">Jingle</a>s.
 * @author Berend de Groot
 * @version 1.1
 */
public class MusicNote {

    //TODO: Fix all typos.
    //TODO: Change whenToPlay into waitAmountForStart where appropriate.
    //TODO: Update all the documentation.

    private double noteLength;
    private int notePitch;
    private double noteDelay;

    //TODO: Specify which unit the attributes are measured in.
    //TODO: Rename whenToPlay to noteDelay.

    /**
     * Constructor that creates an object of this class.
     * @param noteLength Length of the note (Should be an attribute of an instance of <a href="{@docRoot}/Logic/NoteLengthGenerator.html">NoteLengthGenerator</a>)..
     * @param notePitch Pitch of the note (for example: C4, A#6) (Should be a value generated with <a href="{@docRoot}/Logic/NoteLengthGenerator.html">NotePitchGenerator</a>).
     * @param noteDelay Timing; this is the time it waits after the note before this note is done playing.
     */
    public MusicNote(double noteLength, int notePitch, double noteDelay) {
        this.noteLength = noteLength;
        this.notePitch = notePitch;
        this.noteDelay = noteDelay;
    }

    /**
     * Constructor that creates an object of this class.
     * @param noteLength Length of the note (Should be an attribute of an instance of <a href="{@docRoot}/Logic/NoteLengthGenerator.html">NoteLengthGenerator</a>)..
     * @param notePitch Pitch of the note (for example: C4, A#6) (Should be a value generated with <a href="{@docRoot}/Logic/NoteLengthGenerator.html">NotePitchGenerator</a>).
     */
    public MusicNote(double noteLength, int notePitch) {
        this.noteLength = noteLength;
        this.notePitch = notePitch;
    }

    /**
     * Auto-generated getter for the <code>noteLength</code> attribute.
     * @return noteLength
     */
    public double getNoteLength() {
        return noteLength;
    }

    /**
     * Getter for the <code>noteLength</code> attribute, used by the <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a>.
     * This is because the <code>Buzzer</code> uses an integer (representing MS) for the timing.
     * @return noteLength * 1000 as an integer (so decimals might be lost).
     */
    public int getNoteLengthInMS(){
        return ((int)(this.getNoteLength() * 1000));
    }

    /**
     * Auto-generated getter for the <code>whenToPlay</code> attribute.
     * @return whenToPlay.
     */
    public double getNoteDelay() {
        return this.noteDelay;
    }

    /**
     * Getter for the <code>whenToPlay</code> attribute in MS, used by the <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a>.
     * @return whenToPlay * 1000 as an integer.
     */
    public int getNoteDelayInMS() {
        return ((int)(this.noteDelay * 1000));
    }

    /**
     * Auto-generated setter for the <code>whenToPlay</code> attribute.
     * @param whenToPlay Replaces the current whenToPlay value.
     */
    public void setNoteDelay(double whenToPlay) {
        this.noteDelay = whenToPlay;
    }

    /**
     * Auto-generated getter for the <code>notePitch</code> attribute.
     * @return The pitch of the note (this.notePitch).
     */
    public int getNotePitch() {
        return this.notePitch;
    }

    /**
     * Auto-generated setter for the <code>NoteLength</code> attribute.
     * @param noteLength Replaces the current NoteLength value.
     */
    public void setNoteLength(double noteLength) {
        this.noteLength = noteLength;
    }
}
