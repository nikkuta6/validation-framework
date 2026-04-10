package by.pranovich.validationframework.handlers;

import by.pranovich.validationframework.annotations.Max;
import by.pranovich.validationframework.core.ValidationError;

import java.lang.reflect.Field;
import java.util.List;

public class MaxHandler extends ValidationHandler {
    @Override
    protected void validate(Field field, Object target, List<ValidationError> errors) {
        if (!field.isAnnotationPresent(Max.class)) {
            return;
        }

        Object value = getFieldValue(field, target);
        Max annotation = field.getAnnotation(Max.class);

        if (value == null) {
            return;
        }

        if (!(value instanceof Number number)) {
            errors.add(new ValidationError(field.getName(), "Annotation @Max can be applied only to numeric fields"));
            return;
        }
        if (number.doubleValue() > annotation.value()) {
            errors.add(new ValidationError(field.getName(), annotation.message()));
        }
    }
}
