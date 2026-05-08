package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Positive;
import by.pranovich.validationframework.core.ValidationContext;

public class PositiveHandler extends ValidationHandler {
    @Override
    protected void validate(ValidationContext context) {
        if (!context.hasAnnotation(Positive.class)) {
            return;
        }

        Object value = context.getValue();
        Positive annotation = context.getAnnotation(Positive.class);

        if (value == null) {
            return;
        }

        if (!(value instanceof Number number)) {
            context.addIssue("Annotation @Positive can be applied only to numeric fields.");
            return;
        }

        if (number.doubleValue() <= 0) {
            context.addIssue(annotation.message());
        }
    }
}
