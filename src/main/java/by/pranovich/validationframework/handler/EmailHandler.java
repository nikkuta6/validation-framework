package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Email;
import by.pranovich.validationframework.core.ValidationContext;

public class EmailHandler extends ValidationHandler {
    private static final String VALID_EMAIL_REGEX = "^[\\w.%+-]{1,64}@[A-Za-z0-9-]{1,30}(\\.[A-Za-z0-9-]{2,10}){1,3}$";

    @Override
    protected void validate(ValidationContext context) {
        if (!context.hasAnnotation(Email.class)) {
            return;
        }

        Object value = context.getValue();
        Email annotation = context.getAnnotation(Email.class);

        if (value == null) {
            return;
        }

        if (!(value instanceof String email)) {
            context.addIssue("Annotation @Email can be applied only to String fields.");
            return;
        }

        if (!email.matches(VALID_EMAIL_REGEX)) {
            context.addIssue(annotation.message());
        }
    }
}
