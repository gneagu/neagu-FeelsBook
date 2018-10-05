package cs.ualberta.ca.neagu_feelsbook;

import java.util.ArrayList;
import java.util.Date;

public class CurrentMood implements MoodInterface{
    private Date moodDate;
    private String mood;
    private String message;
    private static final Integer MAX_CHAR = 100;

    CurrentMood(String mood){
        this.mood = mood;
        this.moodDate = new Date();
        this.message = "Default Message";
    }

    CurrentMood(String mood, Date date, String message){
        this.mood = mood;
        this.moodDate = date;
        this.message = message;
    }

    public String getMood() {
        return this.mood;
    }

    // Although this code is written, it is not needed for this assignment
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

    // Need to override toString class because otherwise this defaults to
    // the items toString class which just return the id of the element as a string
    @Override
    public String toString(){
        String returnString = new String();

        returnString = this.getMood();
        returnString += this.getMoodDate();

        return returnString;
    }

}
