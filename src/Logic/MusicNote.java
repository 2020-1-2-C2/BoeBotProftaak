package Logic;

public class MusicNote {

    //TODO: Combine the Buzzer getNote() method with methods in this class by inserting the getNote() method here.
    //TODO: Delete the noteLength presets in AudioPlaySystem.java or in MusicNote.java
    //TODO: Fix all typos.
    //TODO: Change whenToPlay into waitAmountForStart where appropriate.

    private double noteLength;
    private double notePitch;
    private double whenToPlay;

    //All attributes related to the timing based off the BPM
    private double halfNote;
    private double quarterNote;
    private double eightNote;
    private double sixteenthNote;
    private double dottedQuarterNote;
    private double dottedEightNote;
    private double dottedSixteenthNote;
    private double tripletQuarterNote;
    private double tripletEightNote;
    private double tripletSixteenthNote;

    //TODO: Specify which unit the attributes are measured in.

    /**
     * Constructor that created an object of this class.
     * @param noteLength Length of the note
     * @param notePitch Pitch of the note (for example: C4, A#6)
     * @param whenToPlay Timing; the time specifying when the note should play
     */
    public MusicNote(double noteLength, double notePitch, double whenToPlay) {
        this.noteLength = noteLength;
        this.notePitch = notePitch;
        this.whenToPlay = whenToPlay;
    }

    public MusicNote(double noteLength, double notePitch) {
        this.noteLength = noteLength;
        this.notePitch = notePitch;
        this.whenToPlay = 0.00;
    }

    public MusicNote(String noteLength, double BPM, double notePitch, double whenToPlay){
        this(0.00, notePitch, whenToPlay);
        double lengthOfNote = 0.00;

        this.halfNote = 120.00 / BPM;
        this.quarterNote = 60.00 / BPM;
        this.eightNote = 30.00 / BPM;
        this.sixteenthNote = 15.00 / BPM;
        this.dottedQuarterNote = 90.00 / BPM;
        this.dottedEightNote = 45.00 / BPM;
        this.dottedSixteenthNote = 22.50 / BPM;
        this.tripletQuarterNote = 40.00 / BPM;
        this.tripletEightNote = 20.00 / BPM;
        this.tripletSixteenthNote = 10.00 / BPM;

        if (noteLength.equals("halfNote")){
            this.setNoteLength(this.halfNote);
        } else if (noteLength.equals("quarterNote")){
            this.setNoteLength(this.quarterNote);
        } else if (noteLength.equals("eightNote")){
            this.setNoteLength(this.eightNote);
        } else if (noteLength.equals("sixteenthNote")){
            this.setNoteLength(this.sixteenthNote);
        } else if (noteLength.equals("dottedQuarterNote")){
            this.setNoteLength(this.dottedQuarterNote);
        } else if (noteLength.equals("dottedEightMode")){
            this.setNoteLength(this.dottedEightNote);
        } else if (noteLength.equals("dottedSixteenthNote")){
            this.setNoteLength(this.dottedSixteenthNote);
        } else if (noteLength.equals("tripletQuarterNote")){
            this.setNoteLength(this.tripletQuarterNote);
        } else if (noteLength.equals("tripletEightNote")){
            this.setNoteLength(this.tripletEightNote);
        } else if (noteLength.equals("tripletSixteenthNote")){
            this.setNoteLength(this.tripletSixteenthNote);
        }
    }

    /**
     * Auto-generated getter for the noteLength attribute.
     * @return noteLength
     */
    public double getNoteLength() {
        return noteLength;
    }

    /**
     * Auto-generated getter for the notePitch attribute.
     * @return notePitch
     */
    public double getNotePitch() {
        return notePitch;
    }

    /**
     * Auto-generated getter for the whenToPlay attribute.
     * @return whenToPlay
     */
    public double getWhenToPlay() {
        return whenToPlay;
    }

    /**
     * Auto-generated setter for the whenToPlay attribute.
     * @param whenToPlay Replaces the current whenToPlay value.
     */
    public void setWhenToPlay(double whenToPlay) {
        this.whenToPlay = whenToPlay;
    }

    /**
     * Auto-generated setter for the NoteLength attribute.
     * @param noteLength Replaces the current NoteLength value.
     */
    public void setNoteLength(double noteLength) {
        this.noteLength = noteLength;
    }
}
