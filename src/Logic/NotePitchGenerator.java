package Logic;

public class NotePitchGenerator {

    /**
     * Method that returns an integer resembling the pitch (Hz).
     * @param note String containing info about the pitch. It represents a note WITHOUT info about the octave (for example: G#, A, C).
     * @param octave Integer containing info about the pitch. It represents the octave the note should be played at. (for example: 4, 5, 6).
     * @return A rounded frequency (Hz) as an integer, representing a note in music.
     */
    public int getNote(String note, int octave){
        double noteFrequency;
        int noteOctave = octave + 10;

        if (note.contains("C")){
            noteFrequency = 16.35 * noteOctave;
        } else if (note.contains("C#")){
            noteFrequency = 17.32 * noteOctave;
        } else if (note.contains("D")){
            noteFrequency = 18.35 * noteOctave;
        } else if (note.contains("D#")){
            noteFrequency = 19.44 * noteOctave;
        } else if (note.contains("E")){
            noteFrequency = 20.60 * noteOctave;
        } else if (note.contains("F")){
            noteFrequency = 21.82 * noteOctave;
        } else if (note.contains("F#")){
            noteFrequency = 23.12 * noteOctave;
        } else if (note.contains("G")){
            noteFrequency = 24.49 * noteOctave;
        } else if (note.contains("G#")){
            noteFrequency = 25.95 * noteOctave;
        } else if (note.contains("A")){
            noteFrequency = 27.50 * noteOctave;
        } else if (note.contains("A#")){
            noteFrequency = 29.13 * noteOctave;
        } else if (note.contains("B")){
            noteFrequency = 30.86 * noteOctave;
        } else {
            noteFrequency = 440.0;
        }

        return Math.round((float)noteFrequency);
    }

}
