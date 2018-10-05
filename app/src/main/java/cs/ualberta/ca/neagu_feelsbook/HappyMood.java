package cs.ualberta.ca.neagu_feelsbook;

import java.util.Date;

public class HappyMood extends CurrentMood {

    HappyMood() {
        super("HAPPY MOOD");
    }

    HappyMood(String mood, Date date, String message) { super("SAD MOOD", date, message); }

}
