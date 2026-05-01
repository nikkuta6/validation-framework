package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Max;
import by.pranovich.validationframework.core.ValidationIssue;

import java.lang.reflect.Field;
import java.util.List;

public class MaxHandler extends ValidationHandler {
    @Override
    protected void validate(Field field, Object target, List<ValidationIssue> issues) {
        if (!field.isAnnotationPresent(Max.class)) {
            return;
        }

        Object value = getFieldValue(field, target);
        Max annotation = field.getAnnotation(Max.class);

        if (value == null) {
            return;
        }

        if (!(value instanceof Number number)) {
            issues.add(new ValidationIssue(field.getName(), "@Max can be applied only to numeric fields"));
            return;
        }
        if (number.doubleValue() > annotation.value()) {
            issues.add(new ValidationIssue(field.getName(), annotation.message()));
        }
    }
}
