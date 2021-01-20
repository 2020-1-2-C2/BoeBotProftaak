package logic;

/**
 * Class to generate timing for all notes. It does this by calculating how long a specific note
 * (for example: quarterNote) would be in ms at a given BPM (beats per minute). <p>
 * This class is mainly used by the <a href="{@docRoot}/logic/Jingle.html">Jingle</a> class, which has a couple of
 * <a href="{@docRoot}/logic/AudioPlaySystem.html">AudioPlaySystem</a> objects containing jingles used by the notificationssystem.
 * @author Berend de Groot
 * @version 1.2
 * @see Jingle#connectedJingle()
 * @see AudioPlaySystem
 * @see MusicNote#getNoteLength()
 */
class NoteLengthGenerator {
    //Source of information: https://musescore.org/en/node/22609

    //All attributes related to the timing are based on the BPM. You can see this in the constructor.
    private double halfNote;
    private double quarterNote;
    private double sixteenthNote;

    //NOTE: 60 BPM is one beat per second.
    //NOTE: quarterNotes (60.00) are 1/4 of a bar, which is 240.00

    /**
     * Constructor for the NoteLengthGenerator class. It is used as an instance for other classes to generate timing for all notes. <p>
     * You can find instances of this class in the Jingle.java class. <p>
     * <small>Example: 60 BPM is one beat per second.</small>
     * @param BPM Beats-Per-Minute (double). This is used to calculate the timing for all notes.
     * @see Jingle
     */
    NoteLengthGenerator(double BPM) {
        this.halfNote = 120.00 / BPM;
        this.quarterNote = 60.00 / BPM;
        this.sixteenthNote = 15.00 / BPM;
    }

    /**
     * Auto-generated getter for the halfNote variable.
     * @return halfnote
     */
    double getHalfNote() {
        return this.halfNote;
    }

    /**
     * Auto-generated getter for the quarterNote variable.
     * @return quarterNote
     */
    double getQuarterNote() {
        return this.quarterNote;
    }

    /**
     * Auto-generated getter for the sixteenthNote variable.
     * @return sixteenthNote
     */
    double getSixteenthNote() {
        return this.sixteenthNote;
    }

    //NOTE: Removed all NoteLength attributes we did not use, including Triplets.
}
