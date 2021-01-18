package Logic;

/**
 * Used by other classes to calculate frequencies (Hz).
 * The calculations require a musical note and an octave as input.
 * @author Berend de Groot
 * @version 2.1
 * @see MusicNote#getNotePitch()
 */
public class NotePitchGenerator {

    /**
     * Method that returns an integer resembling the pitch (Hz). This is not completely accurate since it is an
     * integer, and not a double or float.
     * <p>
     * Frequencies are calculated using the following formula: <em>2^N/12 * 440</em>
     * <p>
     * Where:
     * <p><ul>
     * <li>N: The amount of halfsteps/semitones compared to the note A4.
     * <li>12: The amount of halfsteps in an octave (12 halfsteps/semitones).
     * <li>440: The frequency in Hertz of the note A4.
     * </ul><p>
     * @param note String containing info about the pitch. It represents a note without info about the octave (for example: G#, A, C).
     * @param octave Integer containing info about the pitch. It represents the octave the note should be played at. (for example: 4, 5, 6).
     * @return A rounded frequency (Hz) as an integer, representing a note in music.
     */
    public int getNote(String note, int octave){
        double noteFrequency;
        int halfStepDifference = 0; //This is the value assigned by default, and use by "A" or an invalid letter.

        //Checks what note it is and assigns the right value.
        if (note.contains("C")){
            halfStepDifference -= 9;
        } else if (note.contains("C#")){
            halfStepDifference -= 8;
        } else if (note.contains("D")){
            halfStepDifference -= 7;
        } else if (note.contains("D#")){
            halfStepDifference -= 6;
        } else if (note.contains("E")){
            halfStepDifference -= 5;
        } else if (note.contains("F")){
            halfStepDifference -= 4;
        } else if (note.contains("F#")){
            halfStepDifference -= 3;
        } else if (note.contains("G")){
            halfStepDifference -= 2;
        } else if (note.contains("G#")){
            halfStepDifference -= 1; //NOTE: This skips "A" because the halfStepDifference would be 0, which is the default value.
        } else if (note.contains("A#")){
            halfStepDifference += 1;
        } else if (note.contains("B")){
            halfStepDifference += 2;
        }

        //Formulas used to calculate the frequency.
        halfStepDifference += (octave - 4) * 12;
        noteFrequency = Math.pow(2.0, (halfStepDifference/12.0)) * 440.0;
        return Math.round((float)noteFrequency);
    }

}
