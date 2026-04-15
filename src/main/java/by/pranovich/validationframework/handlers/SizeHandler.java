package by.pranovich.validationframework.handlers;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import by.pranovich.validationframework.annotations.Size;
import by.pranovich.validationframework.core.ValidationError;

public class SizeHandler extends ValidationHandler {

    @Override
    protected void validate(Field field, Object target, List<ValidationError> errors) {
        if (!field.isAnnotationPresent(Size.class)) {
            return;
        }

        Object value = getFieldValue(field, target);
        Size annotation = field.getAnnotation(Size.class);

        if (value == null) {
            return;
        }

        int size = getSize(value);
        if (size < 0) {
            errors.add(new ValidationError(
                    field.getName(),
                    "Annotation @Size can be applied only to CharSequence, array, Collection, or Map fields"));
            return;
        }

        if (size < annotation.min() || size > annotation.max()) {
            errors.add(new ValidationError(field.getName(), annotation.message()));
        }
    }

    private static int getSize(Object value) {
        if (value instanceof CharSequence cs) {
            return cs.length();
        }
        if (value.getClass().isArray()) {
            return Array.getLength(value);
        }
        if (value instanceof Collection<?> c) {
            return c.size();
        }
        if (value instanceof Map<?, ?> m) {
            return m.size();
        }
        return -1;
    }
}
