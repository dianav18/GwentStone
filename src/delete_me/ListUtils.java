package delete_me;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ListUtils {

    public static <T> String toString(List<T> list, ReturnArgLambdaExecutor<String, T> toString) {
        return toString(list, toString, s -> s);
    }

    public static <T> String toString(List<T> list, ReturnArgLambdaExecutor<String, T> toString, ReturnArgLambdaExecutor<String, String> formatter) {
        return "[" + list.stream().filter(Objects::nonNull).map(toString::execute).reduce("", (a, b) -> {
            b = formatter.execute(b);

            if (a.isEmpty()) {
                return a + b;
            }
            return a + ", " + b;
        }) + "]";
    }

    public static <T> String toString(Stream<T> list, ReturnArgLambdaExecutor<String, T> toString) {
        return toString(list.collect(Collectors.toList()), toString);
    }


}
