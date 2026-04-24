package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.NotNull;
import by.pranovich.validationframework.core.ValidationIssue;

import java.lang.reflect.Field;
import java.util.List;

public class NotNullHandler extends ValidationHandler {

    @Override
    protected void validate(Field field, Object target, List<ValidationIssue> issues) {
        if (!field.isAnnotationPresent(NotNull.class)) {
            return;
        }

        Object value = getFieldValue(field, target);
        NotNull annotation = field.getAnnotation(NotNull.class);

        if (value == null) {
            issues.add(new ValidationIssue(field.getName(), annotation.message()));
        }
    }
}
