package cs.ualberta.ca.neagu_feelsbook;

import java.util.Date;

public class LoveMood extends CurrentMood{

    LoveMood() { super("LOVE MOOD"); }

    LoveMood(String mood, Date date, String message) { super("LOVE MOOD", date, message); }
}