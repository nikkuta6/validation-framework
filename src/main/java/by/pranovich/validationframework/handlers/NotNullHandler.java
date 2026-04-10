package by.pranovich.validationframework.handlers;

import by.pranovich.validationframework.annotations.NotNull;
import by.pranovich.validationframework.core.ValidationError;

import java.lang.reflect.Field;
import java.util.List;

public class NotNullHandler extends ValidationHandler {

    @Override
    protected void validate(Field field, Object target, List<ValidationError> errors) {
        if (!field.isAnnotationPresent(NotNull.class)) {
            return;
        }

        Object value = getFieldValue(field, target);
        NotNull annotation = field.getAnnotation(NotNull.class);

        if (value == null) {
            errors.add(new ValidationError(field.getName(), annotation.message()));
        }
    }
}
