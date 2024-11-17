package delete_me;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The type Message builder.
 */
public class MessageBuilder extends GenericMessageBuilder<String> {

    /**
     * Instantiates a new Message builder.
     *
     * @param base the base
     */
    public MessageBuilder(final String base) {
        super(base);
    }

    /**
     * Instantiates a new Message builder.
     *
     * @param base         the base
     * @param placeholders the placeholders
     * @param values       the values
     */
    protected MessageBuilder(final String base, final List<Object> placeholders, final List<Object> values) {
        super(base, placeholders, values);
    }

    @Override
    protected boolean equals(final String o1, final String o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }

    @Override
    protected String convertToString() {
        return parse();
    }

    @Override
    protected String parsePlaceholder(final String base, final String placeholder, final String value) {
        if (base == null) {
            return null;
        }
        return base.replace(placeholder, value);
    }

    @Override
    public GenericMessageBuilder<String> clone() {
        return new MessageBuilder(base, new ArrayList<>(placeholders), new ArrayList<>(values));
    }

    public MessageBuilder parse(final Map<?, ?> placeholders) {
        return (MessageBuilder) super.parse(placeholders);
    }

    public MessageBuilder parse(final Object placeholder, final Object value) {
        return (MessageBuilder) super.parse(placeholder, value);
    }
}
