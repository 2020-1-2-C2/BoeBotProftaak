package logic;

/**
 * Class containing <a href="{@docRoot}/logic/AudioPlaySystem.html">AudioPlaySystem</a> instances for all jingles used by the BoeBot.
 * Jingles use different <code>MusicNote</code> instances and have a different BPM (the tempo).
 * Jingles (instances of <a href="{@docRoot}/logic/AudioPlaySystem.html">AudioPlaySystem</a>) are played
 * with the <code>playSong()</code> method in <a href="{@docRoot}/hardware/Buzzer.html">Buzzer</a>.
 * @author Berend de Groot
 * @version 2.0
 * @see AudioPlaySystem#AudioPlaySystem()
 */
public class Jingle {

    private NotePitchGenerator notePitchGenerator = new NotePitchGenerator();

    /**
     * <a href="{@docRoot}/logic/AudioPlaySystem.html">AudioPlaySystem</a> containing the first part of the
     * melody of <i>Somebody That I Used To Know</i> by <i>Gotye</i>.
     * @return audioPlaySystem
     */
    public AudioPlaySystem somebodyThatIUsedToKnow() {
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
     * <a href="{@docRoot}/logic/AudioPlaySystem.html">AudioPlaySystem</a> containing the first part of the melody
     * of <i>Electro019-5</i>, an unfinished track by Berend de Groot. <p>
     * This <code>AudioPlaySystem</code> is used by <a href="{@docRoot}/logic/notification/ConnectedNotification.html">ConnectedNotification</a>.
     * @return audioPlaySystem
     */
    public AudioPlaySystem connectedJingle() {
        NoteLengthGenerator noteLengthGenerator = new NoteLengthGenerator(128);
        AudioPlaySystem audioPlaySystem = new AudioPlaySystem();

        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("D#", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("F#", 5),
                noteLengthGenerator.getQuarterNote() + noteLengthGenerator.getSixteenthNote()));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("C#", 5),
                noteLengthGenerator.getSixteenthNote()));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), this.notePitchGenerator.getNote("G#", 5),
                noteLengthGenerator.getQuarterNote()));
        return audioPlaySystem;
    }

    /**
     * <a href="{@docRoot}/logic/AudioPlaySystem.html">AudioPlaySystem</a> containing the first part of the melody
     * of <i>Electro018-3</i>, an unfinished track by Berend de Groot. <p>
     * This <code>AudioPlaySystem</code> is used by <a href="{@docRoot}/logic/notification/DisconnectedNotification.html">DisconnectedNotification</a>.
     * @return audioPlaySystem
     */
    public AudioPlaySystem disconnectedJingle() {
        NoteLengthGenerator noteLengthGenerator = new NoteLengthGenerator(128);
        AudioPlaySystem audioPlaySystem = new AudioPlaySystem();

        for (int i = 0; i < 4; i++) { //First part of the melody repeats itself
            audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("F", 4)));
            audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("G#", 4)));
            audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("C", 4)));
        }
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("F", 4)));
        return audioPlaySystem;
    }

    /**
     * <a href="{@docRoot}/logic/AudioPlaySystem.html">AudioPlaySystem</a> containing the jingle used by
     * <a href="{@docRoot}/logic/notification/EmergencyStopNotification.html">EmergencyStopNotification</a>.
     * @return audioPlaySystem
     * @see DriveSystem#emergencyStop()
     */
    public AudioPlaySystem emergencyJingle() {
        NoteLengthGenerator noteLengthGenerator = new NoteLengthGenerator(119);
        AudioPlaySystem audioPlaySystem = new AudioPlaySystem();

        //Don't worry about the duplicate code here.
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("A", 4)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("B", 4)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("C", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("A", 4)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), this.notePitchGenerator.getNote("E", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("A", 4), noteLengthGenerator.getHalfNote()));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("B", 4)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("C", 5)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getSixteenthNote(), this.notePitchGenerator.getNote("A", 4)));
        audioPlaySystem.addNote(new MusicNote(noteLengthGenerator.getQuarterNote(), this.notePitchGenerator.getNote("E", 5)));
        return audioPlaySystem;
    }

}
