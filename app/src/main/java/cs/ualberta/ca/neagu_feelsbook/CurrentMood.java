/**
 * Current Mood
 *
 * @Version: 1.0
 * @Date: Oct 2, 2018
 */
package cs.ualberta.ca.neagu_feelsbook;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This is a super class which creates CurrentMood objects, and contains the super functions.
 */
public class CurrentMood implements MoodInterface,Comparable<CurrentMood>  {
    private Date moodDate;
    private String mood;
    private String message;
    private static final Integer MAX_CHAR = 100;

    /**
     * Constructor that creates a CurrentMood object
     *
     * @param mood
     */
    CurrentMood(String mood){
        this.mood = mood;
        this.moodDate = new Date();
        this.message = "";
    }

    /**
     * Constructor that creates a CurrentMood object.
     *
     * @param mood
     * @param date
     * @param message
     */
    CurrentMood(String mood, Date date, String message){
        this.mood = mood;
        this.moodDate = date;
        this.message = message;
    }

    /**
     * Function that return the mood of the object.
     *
     * @return mood
     */
    public String getMood() {
        return this.mood;
    }

    // Although this code is written, it is not needed for this assignment
    public void setMood(String mood) {
        this.mood = mood;
    }

    /**
     * Function that return the date of the object.
     *
     * @return mood
     */
    public Date getMoodDate() {
        return this.moodDate;
    }

    /**
     * Function that sets the date of the object.
     *
     * @return moodDate
     */
    public void setMoodDate(Date moodDate) {
        this.moodDate = moodDate;
    }

    /**
     * Function that returns the message of the object
     *
     * @return message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Function that return the mood of the object.
     *
     * @return mood
     */
    public void setMessage(String message) {
        if (message.length() <= MAX_CHAR) {
            this.message = message;
        } else {
            this.message = "";
        }
    }

    /**
     * Need to override toString class because otherwise this defaults to
     * the items toString class which just return the id of the element as a string.
     * This function returns a string up to 50 characters long that includes the emotion type,
     * the date, and part of the message.
     *
     * @attribution: Mincong Huang
     * @return: String
     */
    @Override
    public String toString(){
        String returnString = new String();

        returnString = this.getMood();

        // Code from Mincong Huang
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String text = sdf.format(this.getMoodDate());

        returnString += " " + text + " " + this.getMessage();

        // This makes sure that no more than 50 characters are shown.
        if (returnString.length() > 50) {
            returnString = returnString.substring(0, 49);
        }

        return returnString;
    }

    /**
     * This function allows for the comparison of date objects.
     *
     * @attribution Phrogz
     * @param o
     * @return
     */
    public int compareTo(@NonNull CurrentMood o) {
        return getMoodDate().compareTo(o.getMoodDate());
    }

}
