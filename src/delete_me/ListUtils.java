package delete_me;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * The type List utils.
 */
public class ListUtils {

    /**
     * To string string.
     *
     * @param <T>      the type parameter
     * @param list     the list
     * @param toString the to string
     * @return the string
     */
    public static <T> String toString(final List<T> list, final ReturnArgLambdaExecutor<String, T> toString) {
        return toString(list, toString, s -> s);
    }

    /**
     * To string string.
     *
     * @param <T>       the type parameter
     * @param list      the list
     * @param toString  the to string
     * @param formatter the formatter
     * @return the string
     */
    public static <T> String toString(final List<T> list, final ReturnArgLambdaExecutor<String, T> toString, final ReturnArgLambdaExecutor<String, String> formatter) {
        return "[" + list.stream().filter(Objects::nonNull).map(toString::execute).reduce("", (a, b) -> {
            b = formatter.execute(b);

            if (a.isEmpty()) {
                return a + b;
            }
            return a + ", " + b;
        }) + "]";
    }

    /**
     * To string string.
     *
     * @param <T>      the type parameter
     * @param list     the list
     * @param toString the to string
     * @return the string
     */
    public static <T> String toString(final Stream<T> list, final ReturnArgLambdaExecutor<String, T> toString) {
        return toString(list.collect(Collectors.toList()), toString);
    }


}
