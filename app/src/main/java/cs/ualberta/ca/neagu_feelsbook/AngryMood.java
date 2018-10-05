/**
 * AngryMood
 *
 * @Version: 1.0
 * @Date: Oct 2, 2018
 */
package cs.ualberta.ca.neagu_feelsbook;

import java.util.Date;

/**
 * This class is a subclass of the Current Mood, and
 */
public class AngryMood extends CurrentMood {

    /**
     * Constructor that takes a string as ana argument and creates a AngryMood.
     *
     * @Argument: string
     */
    AngryMood() { super("ANGRY MOOD"); }

    /**
     * Constructor that takes a string as ana argument and creates a AngryMood.
     *
     * @param mood
     * @param date
     * @param message
     */
    AngryMood(String mood, Date date, String message) { super("ANGRY MOOD", date, message); }
}
