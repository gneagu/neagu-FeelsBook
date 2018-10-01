package cs.ualberta.ca.neagu_feelsbook;

import java.util.Date;

public class HappyMood extends CurrentMood {
    String moodMessage = new String();
    private Date moodDate;
    private String mood;

    HappyMood() {
        super();
        this.mood = "happy_mood";
    }

    HappyMood(Date moodDate) {
        super(moodDate);
        this.mood = "happy_mood";
    }

    public Date getMoodDate() {
        return this.moodDate;
    }

    public void setMoodDate(Date moodDate) {
        this.moodDate = moodDate;
    }

    public String getMoodType() {
        return this.mood;
    }

    public void setMoodType() { this.mood = "happy_mood"; }
}
