package delete_me;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The type Generic message builder.
 *
 * @param <T> the type parameter
 */
public abstract class GenericMessageBuilder<T> {

    /**
     * The Base.
     */
    @Getter
    protected T base;
    /**
     * The Placeholders.
     */
    protected List<Object> placeholders = new ArrayList<>();
    /**
     * The Values.
     */
    protected List<Object> values = new ArrayList<>();


    /**
     * Instantiates a new Generic message builder.
     *
     * @param base the base
     */
    public GenericMessageBuilder(final T base) {
        this.base = base;
    }

    /**
     * Instantiates a new Generic message builder.
     *
     * @param base         the base
     * @param placeholders the placeholders
     * @param values       the values
     */
    protected GenericMessageBuilder(final T base, final List<Object> placeholders, final List<Object> values) {
        this.base = base;
        this.placeholders = placeholders;
        this.values = values;
    }

    /**
     * Parse generic message builder.
     *
     * @param placeholders the placeholders
     * @return the generic message builder
     */
    public GenericMessageBuilder<T> parse(final Map<?, ?> placeholders) {
        GenericMessageBuilder<T> working = this;
        for (final Object placeholder : placeholders.keySet()) {
            final String value = placeholders.get(placeholder).toString();
            working = working.parse(placeholder, value);
        }
        return working;
    }

    /**
     * Replace generic message builder.
     *
     * @param placeholder the placeholder
     * @param value       the value
     * @return the generic message builder
     */
    public GenericMessageBuilder<T> replace(final Object placeholder, final Object value) {
        return parse(placeholder, value);
    }

    /**
     * Parse generic message builder.
     *
     * @param placeholder the placeholder
     * @param value       the value
     * @return the generic message builder
     */
    public GenericMessageBuilder<T> parse(final Object placeholder, final Object value) {
        final GenericMessageBuilder<T> working = clone();

        working.placeholders.add(placeholder);
        working.values.add(value);
        return working;
    }

    /**
     * Parse t.
     *
     * @return the t
     */
    public T parse() {
        T parsed = base;

        for (int i = 0; i < Math.min(placeholders.size(), values.size()); i++) {
            String placeholder = null;
            final Object placeholderObj = placeholders.get(i);

            String value = "null";
            final Object valueObj = values.get(i);

            if (placeholderObj != null) {
                placeholder = placeholderObj.toString();
            }
            if (valueObj != null) {
                value = valueObj.toString();
            }

            if (placeholder == null) {
                continue;
            }

            if (placeholder.startsWith("%") && placeholder.endsWith("%")) {
                placeholder = placeholder.substring(1, placeholder.length() - 1);
            }

            parsed = parsePlaceholder(parsed, "%" + placeholder + "%", value);
            parsed = parsePlaceholder(parsed, "{" + placeholder + "}", value);
        }

        this.base = parsed;
        return parsed;
    }

    @Override
    public String toString() {
        return convertToString();
    }

    /**
     * Compares 2 instances of T and asserts if they are equal.
     *
     * @param o1 First instance of T.
     * @param o2 Second instance of T.
     * @return True if equal, false otherwise.
     */
    @SuppressWarnings("unused")
    protected abstract boolean equals(T o1, T o2);

    /**
     * Convert to string string.
     *
     * @return the string
     */
    protected abstract String convertToString();

    /**
     * Parse placeholder t.
     *
     * @param base        the base where to parse into
     * @param placeholder The placeholder to parse. Already has the identifier with % at beginning and end
     * @param value       The value to parse the placeholder with
     * @return The parsed value
     */
    protected abstract T parsePlaceholder(T base, String placeholder, String value);

    /**
     * Clones the current object
     */
    public abstract GenericMessageBuilder<T> clone();

}
