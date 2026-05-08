package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.NotBlank;
import by.pranovich.validationframework.core.ValidationContext;

public class NotBlankHandler extends ValidationHandler {

    @Override
    protected void validate(ValidationContext context) {
        if (!context.hasAnnotation(NotBlank.class)) {
            return;
        }

        Object value = context.getValue();
        NotBlank annotation = context.getAnnotation(NotBlank.class);

        if (value == null) {
            context.addIssue(annotation.message());
            return;
        }

        if (!(value instanceof CharSequence text)) {
            context.addIssue("Annotation @NotBlank can be applied only to CharSequence fields.");
            return;
        }

        if (text.toString().isBlank()) {
            context.addIssue(annotation.message());
        }
    }
}
