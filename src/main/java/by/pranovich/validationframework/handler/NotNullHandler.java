package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.NotNull;
import by.pranovich.validationframework.core.ValidationContext;

public class NotNullHandler extends ValidationHandler {

    @Override
    protected void validate(ValidationContext context) {
        if (!context.hasAnnotation(NotNull.class)) {
            return;
        }

        Object value = context.getValue();
        NotNull annotation = context.getAnnotation(NotNull.class);

        if (value == null) {
            context.addIssue(annotation.message());
        }
    }
}
