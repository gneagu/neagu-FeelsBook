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

    HappyMood(Date moodDate, String message) {
        super(moodDate, message);
        this.mood = "happy_mood";
    }
}
