package Logic;

import java.util.ArrayList;

public class AudioPlaySystem {

    //TODO: Delete the noteLength presets in AudioPlaySystem.java or in MusicNote.java
    //TODO: Fix all typos.

    //Source of information: https://musescore.org/en/node/22609

    private double BPM;
    private ArrayList<MusicNote> notesToPlay = new ArrayList();

    //All attributes related to the timing based off the BPM.
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

    public AudioPlaySystem(double BPM){
        this.BPM = BPM;
        this.notesToPlay = new ArrayList<>();

        //All attributes related to the timing based off the BPM.
        this.halfNote = 120.00 / this.BPM;
        this.quarterNote = 60.00 / this.BPM;
        this.eightNote = 30.00 / this.BPM;
        this.sixteenthNote = 15.00 / this.BPM;
        this.dottedQuarterNote = 90.00 / this.BPM;
        this.dottedEightNote = 45.00 / this.BPM;
        this.dottedSixteenthNote = 22.50 / this.BPM;
        this.tripletQuarterNote = 40.00 / this.BPM;
        this.tripletEightNote = 20.00 / this.BPM;
        this.tripletSixteenthNote = 10.00 / this.BPM;
    }

    public AudioPlaySystem(double BPM, ArrayList<MusicNote> notesToPlay){
        this.BPM = BPM;
        this.notesToPlay = notesToPlay;

        //All attributes related to the timing based off the BPM.
        //NOTE: 60 BPM is one second.
        //NOTE: quarternotes (60.00) are 1/4 of a bar, which is 240.00
        this.halfNote = 120.00 / this.BPM;
        this.quarterNote = 60.00 / this.BPM;
        this.eightNote = 30.00 / this.BPM;
        this.sixteenthNote = 15.00 / this.BPM;
        this.dottedQuarterNote = 90.00 / this.BPM;
        this.dottedEightNote = 45.00 / this.BPM;
        this.dottedSixteenthNote = 22.50 / this.BPM;
        this.tripletQuarterNote = 40.00 / this.BPM;
        this.tripletEightNote = 20.00 / this.BPM;
        this.tripletSixteenthNote = 10.00 / this.BPM;
    }


    public void addNote(MusicNote musicNote){
        if (!this.notesToPlay.isEmpty()){
            this.notesToPlay.add(new MusicNote(musicNote.getNoteLength(), musicNote.getNotePitch(), this.notesToPlay.get(this.notesToPlay.size() - 1).getWhenToPlay() + this.notesToPlay.get(this.notesToPlay.size() - 1).getNoteLength() + musicNote.getWhenToPlay()));
        } else {
            this.notesToPlay.add(new MusicNote(musicNote.getNoteLength(), musicNote.getNotePitch(), 0));
        }
    }

    public void addNotes(ArrayList<MusicNote> musicNotes){
        this.notesToPlay.addAll(musicNotes);
    }

    public void addNote(String noteLength, double BPM, double notePitch, double whenToPlay){
        addNote(new MusicNote(noteLength, BPM, notePitch, whenToPlay));
    }

    public void addNote(double noteLength, double notePitch){
        addNote(new MusicNote(noteLength, notePitch));
    }

}
