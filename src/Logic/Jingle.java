package Logic;

/**
 * Class containing <a href="{@docRoot}/Logic/AudioPlaySystem.html">AudioPlaySystem</a> instances for all jingles used by the BoeBot.
 * Jingles use different <code>MusicNote</code> instances and have a different BPM (the tempo).
 * Jingles (instances of <a href="{@docRoot}/Logic/AudioPlaySystem.html">AudioPlaySystem</a>) are played
 * with the <code>playSong()</code> method in <a href="{@docRoot}/Hardware/Buzzer.html">Buzzer</a>.
 * @author Berend de Groot
 * @version 2.0
 * @see AudioPlaySystem#AudioPlaySystem()
 */
public class Jingle {

    private NotePitchGenerator notePitchGenerator = new NotePitchGenerator();

    /**
     * <a href="{@docRoot}/Logic/AudioPlaySystem.html">AudioPlaySystem</a> containing the first part of the
     * melody of <i>Somebody That I Used To Know</i> by <i>Gotye</i>.
     * @return audioPlaySystem
     */
    public AudioPlaySystem somebodyThatIUsedToKnow(){
        NoteLengthGenerator noteLengthGenerator = new NoteLengthGenerator(129);
        AudioPlaySystem audioPlaySystem = new AudioPlaySystem();

        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getEightNote(), this.notePitchGenerator.getNote("F", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getEightNote(), this.notePitchGenerator.getNote("F", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getEightNote(), this.notePitchGenerator.getNote("G", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getEightNote(), this.notePitchGenerator.getNote("G", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("A", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("A#", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("C", 6)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("A", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getHalfNote(), this.notePitchGenerator.getNote("G", 5)));
        return audioPlaySystem;
    }


    /**
     * <a href="{@docRoot}/Logic/AudioPlaySystem.html">AudioPlaySystem</a> containing the first part of the melody of <i>Brother John</i>.
     * @return audioPlaySystem
     */
    public AudioPlaySystem brotherJohn(){
        NoteLengthGenerator noteLengthGenerator = new NoteLengthGenerator(150);
        AudioPlaySystem audioPlaySystem = new AudioPlaySystem();

        for (int i = 0; i < 2; i++ ){ //First part of the melody repeats itself
            audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), this.notePitchGenerator.getNote("G", 4)));
            audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), this.notePitchGenerator.getNote("A", 4)));
            audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), this.notePitchGenerator.getNote("B", 4)));
            audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), this.notePitchGenerator.getNote("G", 4)));
        }
        return audioPlaySystem;
    }

}
