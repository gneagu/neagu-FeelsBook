/**
 * HappyMood
 *
 * @Version: 1.0
 * @Date: Oct 2, 2018
 */
package cs.ualberta.ca.neagu_feelsbook;

import java.util.Date;

public class JoyMood extends CurrentMood {

    /**
     * Constructor that takes a string as ana argument and creates a HappyMood
     *
     * @Argument: string
     */
    JoyMood() {
        super("JOY MOOD");
    }

    /**
     * Constructor that takes a string as ana argument and creates a HappyMood
     *
     * @param mood
     * @param date
     * @param message
     */
    JoyMood(String mood, Date date, String message) { super("JOY MOOD", date, message); }

}