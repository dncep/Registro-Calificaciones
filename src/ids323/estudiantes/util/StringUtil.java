package ids323.estudiantes.util;

/**
 * Things for strings.
 */
public class StringUtil {

    public static String ellipsis(String str, int max) {
        if (str.length() > max) {
            return (str.substring(0, max - 3) + "...").intern();
        } else {
            return str;
        }
    }

    private StringUtil() {}
}
