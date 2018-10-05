/**
 * LoveMood
 *
 * @Version: 1.0
 * @Date: Oct 2, 2018
 */
package cs.ualberta.ca.neagu_feelsbook;

import java.util.Date;

public class LoveMood extends CurrentMood{

    /**
     * Constructor that takes a string as ana argument and creates a LoveMood
     *
     * @Argument: string
     */
    LoveMood() { super("LOVE MOOD"); }

    /**
     * Constructor that takes a string as ana argument and creates a LoveMood
     *
     * @param mood
     * @param date
     * @param message
     */
    LoveMood(String mood, Date date, String message) {
        super("LOVE MOOD", date, message);
    }
}