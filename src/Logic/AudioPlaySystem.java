package Logic;

import java.util.ArrayList;

public class AudioPlaySystem {

    private ArrayList<MusicNote> notesToPlay = new ArrayList();

    public AudioPlaySystem(){
        this.notesToPlay = new ArrayList<>();
    }

    public AudioPlaySystem(ArrayList<MusicNote> notesToPlay){
        this.notesToPlay = notesToPlay;
    }

    public void clearNotes(){
        this.notesToPlay.clear();
    }

    public void addNote(MusicNote musicNote){
        if (!this.notesToPlay.isEmpty()){
            this.notesToPlay.add(new MusicNote(musicNote.getNoteLength(), musicNote.getNotePitch(),
                            + this.notesToPlay.get(this.notesToPlay.size() - 1).getNoteLength()
                            + musicNote.getWhenToPlay()
            ));
        } else {
            this.notesToPlay.add(musicNote);
        }
    }

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
