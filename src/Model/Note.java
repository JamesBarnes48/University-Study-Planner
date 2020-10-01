package Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Note implements Serializable {
    private static final long serialVersionUID = -5L;
    private String title;
    private LocalDateTime timeStamp;
    private String noteText;

    public Note(String title,  LocalDateTime timeStamp, String text)
    {
        this.title = title;
        this.timeStamp = timeStamp;
        this.noteText = text;
    }

    public String getTitle() { return title; }
    public LocalDateTime getTimeStamp() { return timeStamp; }
    public String getNoteText(){ return noteText; }

    public void setTitle(String newTitle){
        title = newTitle;
    }
    public void setNoteText(String newNoteText){
        noteText = newNoteText;
    }
}
