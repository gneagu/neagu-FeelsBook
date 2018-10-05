/**
 * SurpriseMood
 *
 * @Version: 1.0
 * @Date: Oct 2, 2018
 */
package cs.ualberta.ca.neagu_feelsbook;

import java.util.Date;

public class SurpriseMood extends CurrentMood {

    /**
     * Constructor that takes a string as ana argument and creates a SurpriseMood
     *
     * @Argument: string
     */
    SurpriseMood() {
        super("SURPRISE MOOD");
    }

    /**
     * Constructor that takes a string as ana argument and creates a SurpriseMood
     *
     * @param mood
     * @param date
     * @param message
     */
    SurpriseMood(String mood, Date date, String message) { super("SURPRISE MOOD", date, message); }
}
