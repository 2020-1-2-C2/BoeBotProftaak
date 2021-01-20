package hardware;

import logic.AudioPlaySystem;
import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import utils.Updatable;

/**
 * <code>Buzzer</code> class, from which all buzzer sensors should have its own instance. <p>
 * The methods are used to play sounds on the <code>Buzzer</code>.
 * @see utils.Updatable
 * @see logic.NotePitchGenerator
 * @see logic.AudioPlaySystem
 * @author Berend de Groot, Lars Hoendervangers, Martijn de Kam, Meindert Kempe
 * @version 2.1
 */
public class Buzzer implements Updatable {
    private int pinId;
    private Timer noteDelayTimer;
    private int currentNoteCount;
    private AudioPlaySystem selectedSong;

    /**
     * Constructor for the buzzer hardware component, deriving from the <code>Buzzer</code> class.
     * @param pinId An integer representing the pin whom the buzzer is connected to.
     */
    public Buzzer(int pinId) {
        this.pinId = pinId;
        BoeBot.setMode(pinId, PinMode.Output);
        this.selectedSong = null;
        //Note: Line below is infinite.
        this.noteDelayTimer = new Timer(0);
        this.currentNoteCount = 0;
    }

    /**
     * Makes the <code>Buzzer</code> buzz for a certain amount of time on a certain frequency.
     * @param time Time in milliseconds. Buzzer stops buzzing after the timer has passed.
     * @param freq Frequency in Hz.
     * @see BoeBot#freqOut(int, int, int)
     */
    public void buzz(int time, int freq) {
        BoeBot.freqOut(this.pinId, freq, time);
    }

    /**
     * Method used to turn the buzzer off. <p>
     * This will interrupt the <a href="{@docRoot}/logic/Jingle.html">jingle</a>.
     * It does this by setting <code>this.selectedSong</code> to <code>null</code>. <p>
     * If the user wants to play the <code>jingle</code> again he has to start over from the beginning by initiating it again.
     * @see logic.Jingle
     */
    public void off() {
        this.selectedSong = null;
    }

    /**
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * @see Updatable#update()
     */
    @Override
    public void update() {
        if (this.selectedSong != null){
            if (this.currentNoteCount < this.selectedSong.getNotesToPlay().size()){
                if (this.noteDelayTimer.timeout()){
                    buzz(this.selectedSong.getNotesToPlay().get(this.currentNoteCount).getNoteLengthInMS(),
                            this.selectedSong.getNotesToPlay().get(this.currentNoteCount).getNotePitch());
                    this.currentNoteCount++;
                        if (this.selectedSong.getNotesToPlay().size() - 1 > this.currentNoteCount){
                            this.noteDelayTimer.setInterval(this.selectedSong.getNotesToPlay().get(this.currentNoteCount).getNoteDelayInMS()
                                    + this.selectedSong.getNotesToPlay().get(this.currentNoteCount).getNoteLengthInMS());
                        }
                }
            } else {
                this.selectedSong = null;
                this.currentNoteCount = 0;
            }
        }
    }

    /**
     * Plays the song in the <a href="{@docRoot}/logic/AudioPlaySystem.html">AudioPlaySystem</a> given as a parameter.
     * @param audioPlaySystem Method uses the notes from the <code>getNotesToPlay()</code> ArrayList.
     * @see AudioPlaySystem#getNotesToPlay()
     */
    public void playSong(AudioPlaySystem audioPlaySystem){
        this.selectedSong = audioPlaySystem;
        this.currentNoteCount = 0;
        this.noteDelayTimer.setInterval(1);
    }
}
