package by.pranovich.validationframework.handlers;

import by.pranovich.validationframework.core.ValidationError;

import java.lang.reflect.Field;
import java.util.List;

public abstract class ValidationHandler {
    private ValidationHandler next;

    public ValidationHandler linkWith(ValidationHandler next) {
        this.next = next;
        return next;
    }

    public void handle(Field field, Object target, List<ValidationError> errors) {
        validate(field, target, errors);

        if (next != null) {
            next.handle(field, target, errors);
        }
    }

    protected abstract void validate(Field field, Object target, List<ValidationError> errors);

    protected Object getFieldValue(Field field, Object target) {
        field.setAccessible(true);
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
