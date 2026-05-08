package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Min;
import by.pranovich.validationframework.core.ValidationContext;

public class MinHandler extends ValidationHandler {

    @Override
    protected void validate(ValidationContext context) {
        if (!context.hasAnnotation(Min.class)) {
            return;
        }

        Object value = context.getValue();
        Min annotation = context.getAnnotation(Min.class);

        if (value == null) {
            return;
        }

        if (!(value instanceof Number number)) {
            context.addIssue("Annotation @Min can be applied only to numeric fields.");
            return;
        }

        if (number.doubleValue() < annotation.value()) {
            context.addIssue(annotation.message());
        }
    }
}
