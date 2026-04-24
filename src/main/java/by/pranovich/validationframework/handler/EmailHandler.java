package by.pranovich.validationframework.handler;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;

import by.pranovich.validationframework.annotation.Email;
import by.pranovich.validationframework.core.ValidationIssue;

public class EmailHandler extends ValidationHandler {
    private static final Pattern VALID_EMAIL_PATTERN = Pattern.compile(
            "^[\\w.%+-]{1,64}@[A-Za-z0-9-]{1,30}(\\.[A-Za-z0-9-]{2,10}){1,3}$");

    @Override
    protected void validate(Field field, Object target, List<ValidationIssue> issues) {
        if (!field.isAnnotationPresent(Email.class)) {
            return;
        }

        Object value = getFieldValue(field, target);
        Email annotation = field.getAnnotation(Email.class);

        if (value == null) {
            return;
        }

        if (!(value instanceof String email)) {
            issues.add(new ValidationIssue(field.getName(), "Annotation @Email can be applied only to string fields"));
            return;
        }

        if (!VALID_EMAIL_PATTERN.matcher(email).matches()) {
            issues.add(new ValidationIssue(field.getName(), annotation.message()));
        }
    }
}
