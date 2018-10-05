/**
 * SadMood
 *
 * @Version: 1.0
 * @Date: Oct 2, 2018
 */
package cs.ualberta.ca.neagu_feelsbook;

import java.util.Date;

public class SadMood extends CurrentMood {

    /**
     * Constructor that takes a string as ana argument and creates a SadMood
     *
     * @Argument: string
     */
    SadMood() { super("SAD MOOD"); }

    /**
     * Constructor that takes a string as ana argument and creates a SadMood
     *
     * @param mood
     * @param date
     * @param message
     */
    SadMood(String mood, Date date, String message) { super("SAD MOOD", date, message); }

}
