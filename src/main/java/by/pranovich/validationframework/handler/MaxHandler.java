package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Max;
import by.pranovich.validationframework.core.ValidationContext;

public class MaxHandler extends ValidationHandler {
    @Override
    protected void validate(ValidationContext context) {
        if (!context.hasAnnotation(Max.class)) {
            return;
        }

        Object value = context.getValue();
        Max annotation = context.getAnnotation(Max.class);

        if (value == null) {
            return;
        }

        if (!(value instanceof Number number)) {
            context.addIssue("Annotation @Max can be applied only to numeric fields.");
            return;
        }

        if (number.doubleValue() > annotation.value()) {
            context.addIssue(annotation.message());
        }
    }
}
