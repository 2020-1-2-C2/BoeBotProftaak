package Logic;

public class MusicNote {

    //TODO: Fix all typos.
    //TODO: Change whenToPlay into waitAmountForStart where appropriate.
    //TODO: Update all the documentation.

    private double noteLength;
    private int notePitch;
    private double whenToPlay;

    //TODO: Specify which unit the attributes are measured in.
    //TODO: Rename whenToPlay to noteDelay;

    /**
     * Constructor that created an object of this class.
     * @param noteLength Length of the note (Should be an attribute of an instance of NoteLengthGenerator.java)
     * @param notePitch Pitch of the note (for example: C4, A#6) (Should be a value generated with NotePitchGenerator.java)
     * @param whenToPlay Timing; this is the time it waits after the note before this note is done playing.
     */
    public MusicNote(double noteLength, int notePitch, double whenToPlay) {
        this.noteLength = noteLength;
        this.notePitch = notePitch;
        this.whenToPlay = whenToPlay;
    }

    /**
     * Constructor that created an object of this class.
     * @param noteLength Length of the note (Should be an attribute of an instance of NoteLengthGenerator.java)
     * @param notePitch Pitch of the note (for example: C4, A#6) (Should be a value generated with NotePitchGenerator.java)
     */
    public MusicNote(double noteLength, int notePitch) {
        this.noteLength = noteLength;
        this.notePitch = notePitch;
    }

    /**
     * Auto-generated getter for the noteLength attribute.
     * @return noteLength
     */
    public double getNoteLength() {
        return noteLength;
    }

    /**
     * Getter for the noteLength attribute, used by the buzzer. This is because the buzzer uses an integer (representing MS) for the timing.
     * @return noteLength * 1000 as an integer (so decimals might be lost)
     */
    public int getNoteLengthInMS(){
        return ((int)(getNoteLength() * 1000));
    }

    /**
     * Auto-generated getter for the whenToPlay attribute.
     * @return whenToPlay
     */
    public double getWhenToPlay() {
        return whenToPlay;
    }

    /**
     * Getter for the whenToPlay attribute in MS, used by the Buzzer.
     * @return whenToPlay * 1000 as an integer.
     */
    public int getWhenToPlayInMS() {
        return ((int)(whenToPlay * 1000));
    }

    /**
     * Auto-generated setter for the whenToPlay attribute.
     * @param whenToPlay Replaces the current whenToPlay value.
     */
    public void setWhenToPlay(double whenToPlay) {
        this.whenToPlay = whenToPlay;
    }

    /**
     * Auto-generated getter for the notePitch attribute.
     */
    public int getNotePitch() {
        return notePitch;
    }

    /**
     * Auto-generated setter for the NoteLength attribute.
     * @param noteLength Replaces the current NoteLength value.
     */
    public void setNoteLength(double noteLength) {
        this.noteLength = noteLength;
    }
}
