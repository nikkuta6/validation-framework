package by.pranovich.validationframework.handler;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

import by.pranovich.validationframework.annotation.Size;
import by.pranovich.validationframework.core.ValidationIssue;

public class SizeHandler extends ValidationHandler {

    @Override
    protected void validate(Field field, Object target, List<ValidationIssue> issues) {
        if (!field.isAnnotationPresent(Size.class)) {
            return;
        }

        Size annotation = field.getAnnotation(Size.class);

        if (annotation.min() < 0 || annotation.max() < 0) {
            issues.add(new ValidationIssue(field.getName(),
                    "@Size annotation parameters must not be negative"));
            return;
        }

        if (annotation.min() > annotation.max()) {
            issues.add(new ValidationIssue(field.getName(),
                    "@Size annotation has invalid parameters: min should be less than or equal to max"));
            return;
        }

        Object value = getFieldValue(field, target);

        if (value == null) {
            return;
        }

        OptionalInt size = getSize(value);
        if (size.isEmpty()) {
            issues.add(new ValidationIssue(
                    field.getName(),
                    "@Size can be applied only to CharSequence, Array, Collection, or Map fields"));
            return;

        }

        int actualSize = size.getAsInt();

        if (actualSize < annotation.min() || actualSize > annotation.max()) {
            issues.add(new ValidationIssue(field.getName(), annotation.message()));
        }
    }

    private static OptionalInt getSize(Object value) {
        OptionalInt size = OptionalInt.empty();

        if (value instanceof CharSequence cs) {
            size = OptionalInt.of(cs.length());
        } else if (value.getClass().isArray()) {
            size = OptionalInt.of(Array.getLength(value));
        } else if (value instanceof Collection<?> c) {
            size = OptionalInt.of(c.size());
        } else if (value instanceof Map<?, ?> m) {
            size = OptionalInt.of(m.size());
        }

        return size;
    }
}
