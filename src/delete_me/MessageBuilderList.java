package delete_me;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Message builder list.
 */
public class MessageBuilderList extends GenericMessageBuilder<List<String>> {

    /**
     * Can use the separator %%new-line%% to split the message into multiple lines.
     *
     * @param base The base message to use.
     */
    public MessageBuilderList(final List<String> base) {
        super(base);
    }

    /**
     * Instantiates a new Message builder list.
     *
     * @param base         the base
     * @param placeholders the placeholders
     * @param values       the values
     */
    public MessageBuilderList(final List<String> base, final List<Object> placeholders, final List<Object> values) {
        super(base, placeholders, values);
    }

    @Override
    protected boolean equals(final List<String> o1, final List<String> o2) {
        boolean output = true;

        if (o1 == null) {
            return o2 == null;
        }

        if (o1.size() != o2.size()) {
            return false;
        }

        for (int i = 0; i < o1.size(); i++) {
            output = output && o1.get(i).equals(o2.get(i));
        }

        return output;
    }

    @Override
    protected String convertToString() {
        final List<String> parsed = parse();
        final StringBuilder output = new StringBuilder();

        for (final String line : parsed) {
            output
                    .append(line)
                    .append('\n');
        }

        return output.toString();
    }

    @Override
    protected List<String> parsePlaceholder(final List<String> base, final String placeholder, final String value) {
        if (base == null) {
            return new ArrayList<>();
        }

        final List<String> output = new ArrayList<>();

        for (final String line : base) {
            output.add(line.replace(placeholder, value));
        }

        return output;
    }

    @Override
    public MessageBuilderList clone() {
        return new MessageBuilderList(base, new ArrayList<>(placeholders), new ArrayList<>(values));
    }

    public MessageBuilderList parse(final Map<?, ?> placeholders) {
        return (MessageBuilderList) super.parse(placeholders);
    }
}
