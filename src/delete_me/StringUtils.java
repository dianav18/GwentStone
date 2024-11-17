package delete_me;

/**
 * The type String utils.
 */
public class StringUtils {

    /**
     * Pad string string.
     *
     * @param string the string
     * @param length the length
     * @return the string
     */
    public static String padString(final String string, final int length) {
        return string + " ".repeat(length - string.length());
    }

}
