package Logic;

public class NoteLengthGenerator {

    //TODO: Fix all typos.
    //TODO: Add documentation.
    //TODO: Update comments

    //Source of information: https://musescore.org/en/node/22609

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

    //All attributes related to the timing based off the BPM.
    //NOTE: 60 BPM is one second.
    //NOTE: quarternotes (60.00) are 1/4 of a bar, which is 240.00

    public NoteLengthGenerator(double BPM){
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

    public double getHalfNote() {
        return halfNote;
    }

    public double getQuarterNote() {
        return quarterNote;
    }

    public double getEightNote() {
        return eightNote;
    }

    public double getSixteenthNote() {
        return sixteenthNote;
    }

    public double getDottedQuarterNote() {
        return dottedQuarterNote;
    }

    public double getDottedEightNote() {
        return dottedEightNote;
    }

    public double getDottedSixteenthNote() {
        return dottedSixteenthNote;
    }

    public double getTripletQuarterNote() {
        return tripletQuarterNote;
    }

    public double getTripletEightNote() {
        return tripletEightNote;
    }

    public double getTripletSixteenthNote() {
        return tripletSixteenthNote;
    }
}
