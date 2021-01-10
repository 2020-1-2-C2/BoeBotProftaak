package Logic;

import java.util.ArrayList;

/**
 * Instances made from this class are made in Jingle.java for sounds relying on pitch and rhythm (songs). This class contains methods to add notes with the right timings.
 * @author Berend de Groot
 * @version 2.3
 */
public class AudioPlaySystem {

    private ArrayList<MusicNote> notesToPlay;

    /**
     * Constructor that creates a new instance of AudioPlaySystem.
     */
    AudioPlaySystem(){
        this.notesToPlay = new ArrayList<>();
    }

    /**
     * Adds the <a href="{@docRoot}/Logic/MusicNote.html">MusicNote</a> to the list and edits the timing to fit the whole <a href="{@docRoot}/Logic/Jingle.html">jingle</a>.
     * @param musicNote Adds this note to <code>this.notesToPlay</code>.
     */
    void addNote(MusicNote musicNote){
        if (!this.notesToPlay.isEmpty()){
            this.notesToPlay.add(new MusicNote(musicNote.getNoteLength(), musicNote.getNotePitch(),
                            + this.notesToPlay.get(this.notesToPlay.size() - 1).getNoteLength()
                            + musicNote.getNoteDelay()
            ));
        } else {
            this.notesToPlay.add(musicNote);
        }
    }

    /**
     * Auto-generated getter for the notesToPlay attribute
     * @return notesToPlay
     */
    public ArrayList<MusicNote> getNotesToPlay() {
        return this.notesToPlay;
    }
}
