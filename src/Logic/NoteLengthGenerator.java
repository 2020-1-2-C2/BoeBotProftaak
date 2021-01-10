package Logic;

/**
 * Class to generate timing for all notes. It does this by calculating how long a specific note (for example: quarterNote) would be in ms at a given BPM (beats per minute). <p>
 * This class is mainly used by the <a href="{@docRoot}/Logic/Jingle.html">Jingle</a> class, which has a couple of
 * <a href="{@docRoot}/Logic/AudioPlaySystem.html">AudioPlaySystem</a> objects containing jingles used by the notificationssystem.
 * @author Berend de Groot
 * @version 1.1
 * @see Jingle#somebodyThatIUsedToKnow()
 * @see AudioPlaySystem
 * @see MusicNote#getNoteLength()
 */
public class NoteLengthGenerator {
    //Source of information: https://musescore.org/en/node/22609

    //All attributes related to the timing are based on the BPM. You can see this in the constructor.
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

    //NOTE: 60 BPM is one beat per second.
    //NOTE: quarterNotes (60.00) are 1/4 of a bar, which is 240.00

    /**
     * Constructor for the NoteLengthGenerator class. It is used as an instance for other classes to generate timing for all notes. <p>
     * You can find instances of this class in the Jingle.java class. <p>
     * <small>Example: 60 BPM is one beat per second.</small>
     * @param BPM Beats-Per-Minute (double). This is used to calculate the timing for all notes.
     * @see Jingle
     */
    NoteLengthGenerator(double BPM){
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
     * Auto-generated getter for the eightNote variable.
     * @return eightNote
     */
    double getEightNote() {
        return this.eightNote;
    }

    /**
     * Auto-generated getter for the sixteenthNote variable.
     * @return sixteenthNote
     */
    double getSixteenthNote() {
        return this.sixteenthNote;
    }

    /**
     * Auto-generated getter for the dottedQuarterNote variable.
     * @return dottedQuarterNote
     */
    double getDottedQuarterNote() {
        return this.dottedQuarterNote;
    }

    /**
     * Auto-generated getter for the dottedEightNote variable.
     * @return dottedEightNote
     */
    double getDottedEightNote() {
        return this.dottedEightNote;
    }

    /**
     * Auto-generated getter for the dottedSixteenthNote variable.
     * @return dottedSixteenthNote
     */
    double getDottedSixteenthNote() {
        return this.dottedSixteenthNote;
    }

    /**
     * Auto-generated getter for the tripletQuarterNote variable.
     * @return tripletQuarterNote
     */
    double getTripletQuarterNote() {
        return this.tripletQuarterNote;
    }

    /**
     * Auto-generated getter for the tripletEightNote variable.
     * @return tripletEightNote
     */
    double getTripletEightNote() {
        return this.tripletEightNote;
    }

    /**
     * Auto-generated getter for the tripletSixteenthNote variable.
     * @return tripletSixteenthNote
     */
    double getTripletSixteenthNote() {
        return this.tripletSixteenthNote;
    }
}
