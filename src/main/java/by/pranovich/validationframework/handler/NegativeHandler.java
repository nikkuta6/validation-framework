package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Negative;
import by.pranovich.validationframework.core.ValidationContext;

public class NegativeHandler extends ValidationHandler {
    @Override
    protected void validate(ValidationContext context) {
        if (!context.hasAnnotation(Negative.class)) {
            return;
        }

        Object value = context.getValue();
        Negative annotation = context.getAnnotation(Negative.class);

        if (value == null) {
            return;
        }

        if (!(value instanceof Number number)) {
            context.addIssue("Annotation @Negative can be applied only to numeric fields.");
            return;
        }

        if (number.doubleValue() >= 0) {
            context.addIssue(annotation.message());
        }
    }
}
