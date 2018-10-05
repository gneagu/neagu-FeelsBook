package cs.ualberta.ca.neagu_feelsbook;

import java.util.Date;

public class AngryMood extends CurrentMood {

    AngryMood() { super("ANGRY MOOD"); }

    AngryMood(String mood, Date date, String message) { super("ANGRY MOOD", date, message); }
}
