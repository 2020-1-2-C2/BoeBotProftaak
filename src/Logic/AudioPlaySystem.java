package Logic;

import java.util.ArrayList;

//TODO: Check the styling guide and use it as a reference.

/**
 * Instances made from this class are made in Jingle.java for sounds relying on pitch & rhythm (songs). This class contains methods to add notes with the right timings.
 */
public class AudioPlaySystem {

    private ArrayList<MusicNote> notesToPlay = new ArrayList();

    /**
     * Constructor that creates a new instance of AudioPlaySystem.
     */
    public AudioPlaySystem(){
        this.notesToPlay = new ArrayList<>();
    }

    /**
     * Constructor able to create a new instance from already correctly setup MusicNote objects.
     * @param notesToPlay
     */
    public AudioPlaySystem(ArrayList<MusicNote> notesToPlay){
        this.notesToPlay = notesToPlay;
    }

    /**
     * Method that clears the entire notesToPlay list of this object.
     */
    public void clearNotes(){
        this.notesToPlay.clear();
    }

    /**
     * Adds the MusicNote to the list and edits the timing to fit the whole jingle.
     * @param musicNote Adds this note to this.notesToPlay.
     */
    public void addNote(MusicNote musicNote){
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
     * Method used to add a list of objects of the MusicNote class to this.notesToPlay. It does this by iterating through all MusicNote objects and using them as a parameter in the addNote(MusicNote musicNote) function.
     * @param musicNotes Adds the list to this.notesToPlay.
     */
    public void addNotes(ArrayList<MusicNote> musicNotes){
        for (MusicNote musicNote : musicNotes){
            addNote(musicNote);
        }
    }

    /**
     * Auto-generated getter for the notesToPlay attribute
     * @return
     */
    public ArrayList<MusicNote> getNotesToPlay() {
        return notesToPlay;
    }
}
