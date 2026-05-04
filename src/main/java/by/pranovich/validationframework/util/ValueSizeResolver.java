package by.pranovich.validationframework.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.OptionalInt;

public final class ValueSizeResolver {

    private ValueSizeResolver() {
    }

    public static OptionalInt getSize(Object value) {
        OptionalInt size = OptionalInt.empty();

        if (value instanceof CharSequence sequence) {
            size = OptionalInt.of(sequence.length());
        } else if (value.getClass().isArray()) {
            size = OptionalInt.of(Array.getLength(value));
        } else if (value instanceof Collection<?> collection) {
            size = OptionalInt.of(collection.size());
        } else if (value instanceof Map<?, ?> map) {
            size = OptionalInt.of(map.size());
        }

        return size;
    }
}
