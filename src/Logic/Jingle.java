package Logic;

import Hardware.Buzzer;

public class Jingle {

    private NotePitchGenerator notePitchGenerator = new NotePitchGenerator();

    /**
     * AudioPlaySystem containing the first part of the melody of Somebody That I Used To Know by Gotye.
     * @return audioPlaySystem
     */
    public AudioPlaySystem somebodyThatIUsedToKnow(){
        NoteLengthGenerator noteLengthGenerator = new NoteLengthGenerator(129);
        AudioPlaySystem audioPlaySystem = new AudioPlaySystem();

        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.playNote("F", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.playNote("F", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.playNote("G", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.playNote("G", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getEightNote(), notePitchGenerator.playNote("A", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getEightNote(), notePitchGenerator.playNote("A#", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getEightNote(), notePitchGenerator.playNote("C", 6)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getEightNote(), notePitchGenerator.playNote("A", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getHalfNote(), notePitchGenerator.playNote("G", 5)));
        return audioPlaySystem;
    }


    /**
     * AudioPlaySystem containing the first part of the melody of Brother John.
     * @return audioPlaySystem
     */
    public AudioPlaySystem brotherJohn(){
        NoteLengthGenerator noteLengthGenerator = new NoteLengthGenerator(106);
        AudioPlaySystem audioPlaySystem = new AudioPlaySystem();

        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.playNote("G", 4)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.playNote("A", 4)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.playNote("B", 4)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.playNote("A", 4)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.playNote("G", 4)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.playNote("A", 4)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.playNote("B", 4)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), notePitchGenerator.playNote("A", 4)));
        return audioPlaySystem;
    }

}
