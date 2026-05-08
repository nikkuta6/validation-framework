package by.pranovich.validationframework.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.OptionalInt;

public final class ValueSizeResolver {

    private ValueSizeResolver() {
    }

    public static OptionalInt getSize(Object value) {
        return switch (value) {
            case CharSequence sequence -> OptionalInt.of(sequence.length());
            case Object array when array.getClass().isArray() -> OptionalInt.of(Array.getLength(array));
            case Collection<?> collection -> OptionalInt.of(collection.size());
            case Map<?, ?> map -> OptionalInt.of(map.size());
            default -> OptionalInt.empty();
        };
    }
}
