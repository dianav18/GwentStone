package delete_me;

public class StringUtils {

    public static String padString(String string, int length) {
        return string + " ".repeat(length - string.length());
    }

}
