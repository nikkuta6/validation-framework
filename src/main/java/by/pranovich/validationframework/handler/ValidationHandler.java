package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.core.ValidationIssue;
import by.pranovich.validationframework.exception.ValidationException;

import java.lang.reflect.Field;
import java.util.List;

public abstract class ValidationHandler {
    private ValidationHandler next;

    public ValidationHandler linkWith(ValidationHandler next) {
        this.next = next;
        return next;
    }

    public void handle(Field field, Object target, List<ValidationIssue> issues) {
        validate(field, target, issues);

        if (next != null) {
            next.handle(field, target, issues);
        }
    }

    protected abstract void validate(Field field, Object target, List<ValidationIssue> issues);

    protected Object getFieldValue(Field field, Object target) {
        field.setAccessible(true);
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new ValidationException(
                    "Unable to read field '" + field.getName() + "' from " + target.getClass().getName(),
                    e);
        }
    }
}
