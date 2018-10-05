/**
 * FearMood
 *
 * @Version: 1.0
 * @Date: Oct 2, 2018
 */
package cs.ualberta.ca.neagu_feelsbook;

import java.util.Date;

public class FearMood extends CurrentMood {

    /**
     * Constructor that takes a string as ana argument and creates a FearMood
     *
     * @Argument: string
     */
    FearMood() { super("FEAR MOOD"); }

    /**
     * Constructor that takes a string as ana argument and creates a FearMood
     *
     * @param mood
     * @param date
     * @param message
     */
    FearMood(String mood, Date date, String message) { super("FEAR MOOD", date, message); }

}
