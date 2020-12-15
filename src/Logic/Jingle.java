package Logic;

//TODO: Simplify the oode in this class.

/**
 * Class containing AudioPlaySystem instances for all jingles used by the BoeBot. Jingles use different MusicNote instances and have a different BPM (the tempo).
 * Jingles (instances of AudioPlaySystem) are played with the playSong() method in Buzzer.java.
 */
public class Jingle {

    private NotePitchGenerator notePitchGenerator = new NotePitchGenerator();

    /**
     * AudioPlaySystem containing the first part of the melody of Somebody That I Used To Know by Gotye.
     * @return audioPlaySystem
     */
    public AudioPlaySystem somebodyThatIUsedToKnow(){
        NoteLengthGenerator noteLengthGenerator = new NoteLengthGenerator(129);
        AudioPlaySystem audioPlaySystem = new AudioPlaySystem();

        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.getNote("F", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.getNote("F", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.getNote("G", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.getNote("G", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getEightNote(), notePitchGenerator.getNote("A", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getEightNote(), notePitchGenerator.getNote("A#", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getEightNote(), notePitchGenerator.getNote("C", 6)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getEightNote(), notePitchGenerator.getNote("A", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getHalfNote(), notePitchGenerator.getNote("G", 5)));
        return audioPlaySystem;
    }


    /**
     * AudioPlaySystem containing the first part of the melody of Brother John.
     * @return audioPlaySystem
     */
    public AudioPlaySystem brotherJohn(){
        NoteLengthGenerator noteLengthGenerator = new NoteLengthGenerator(106);
        AudioPlaySystem audioPlaySystem = new AudioPlaySystem();

        for (int i = 0; i < 2; i++ ){ //First part of the melody repeats itself
            audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.getNote("G", 4)));
            audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.getNote("A", 4)));
            audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.getNote("B", 4)));
            audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.getNote("A", 4)));
        }
        return audioPlaySystem;
    }

}
