package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Range;
import by.pranovich.validationframework.core.ValidationContext;

public class RangeHandler extends ValidationHandler {

    @Override
    protected void validate(ValidationContext context) {
        if (!context.hasAnnotation(Range.class)) {
            return;
        }

        Object value = context.getValue();
        Range annotation = context.getAnnotation(Range.class);

        if (annotation.min() > annotation.max()) {
            context.addIssue("@Range annotation has invalid parameters: min should be less than or equal to max");
            return;
        }

        if (value == null) {
            return;
        }

        if (!(value instanceof Number number)) {
            context.addIssue("@Range can be applied only to numeric fields");
            return;
        }

        double actualValue = number.doubleValue();
        if (actualValue < annotation.min() || actualValue > annotation.max()) {
            context.addIssue(annotation.message());
        }
    }
}
