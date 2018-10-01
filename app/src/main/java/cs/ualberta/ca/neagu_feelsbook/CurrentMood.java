package cs.ualberta.ca.neagu_feelsbook;

import java.util.ArrayList;
import java.util.Date;

public abstract class CurrentMood implements MoodInterface{
    private Date moodDate;
    private String mood;
    private String message;
    private static final Integer MAX_CHAR = 100;

    CurrentMood(){
        this.mood = new String();
        this.moodDate = new Date();
        this.message = "Default";
    }

    CurrentMood(Date date){
        this.mood = new String();
        this.moodDate = date;
        this.message = "Default";

    }

    public String getMood() {
        return this.mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public Date getMoodDate() {
        return this.moodDate;
    }

    public void setMoodDate(Date moodDate) {
        this.moodDate = moodDate;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {

        if (message.length() <= MAX_CHAR) {
            this.message = message;
        } else {
            this.message = "";
        }
    }

}
