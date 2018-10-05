package cs.ualberta.ca.neagu_feelsbook;

import java.util.Date;

public class SurpriseMood extends CurrentMood {

    SurpriseMood() {
        super("SURPRISE MOOD");
    }

    SurpriseMood(String mood, Date date, String message) { super("SURPRISE MOOD", date, message); }
}
