package by.pranovich.validationframework.handlers;

import by.pranovich.validationframework.annotations.NotNull;
import by.pranovich.validationframework.core.ValidationContext;
import by.pranovich.validationframework.core.ValidationError;

import java.lang.reflect.Field;

public class NotNullHandler extends AbstractValidationHandler {

    @Override
    protected void validate(ValidationContext context) {
        Field field = context.getField();
        NotNull annotation = field.getAnnotation(NotNull.class);

        if (annotation == null) {
            return;
        }

        Object value = context.getFieldValue();

        if (value == null) {
            context.getErrors().add(new ValidationError(field.getName(), annotation.message()));
        }
    }
}
