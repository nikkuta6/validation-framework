package by.pranovich.validationframework.handler;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import by.pranovich.validationframework.annotation.Size;
import by.pranovich.validationframework.core.ValidationIssue;

public class SizeHandler extends ValidationHandler {

    @Override
    protected void validate(Field field, Object target, List<ValidationIssue> issues) {
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
            issues.add(new ValidationIssue(
                    field.getName(),
                    "Annotation @Size can be applied only to CharSequence, Array, Collection, or Map fields"));
            return;
        }

        if (size < annotation.min() || size > annotation.max()) {
            issues.add(new ValidationIssue(field.getName(), annotation.message()));
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
