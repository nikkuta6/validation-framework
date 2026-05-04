package by.pranovich.validationframework.handler;

import java.util.OptionalInt;

import by.pranovich.validationframework.annotation.Size;
import by.pranovich.validationframework.core.ValidationContext;
import by.pranovich.validationframework.util.ValueSizeResolver;

public class SizeHandler extends ValidationHandler {

    @Override
    protected void validate(ValidationContext context) {
        if (!context.hasAnnotation(Size.class)) {
            return;
        }

        Size annotation = context.getAnnotation(Size.class);

        if (annotation.min() < 0 || annotation.max() < 0) {
            context.addIssue("@Size annotation parameters must not be negative");
            return;
        }

        if (annotation.min() > annotation.max()) {
            context.addIssue("@Size annotation has invalid parameters: min should be less than or equal to max");
            return;
        }

        Object value = context.getValue();

        if (value == null) {
            return;
        }

        OptionalInt size = ValueSizeResolver.getSize(value);
        if (size.isEmpty()) {
            context.addIssue("@Size can be applied only to CharSequence, Array, Collection, or Map fields");
            return;
        }

        int actualSize = size.getAsInt();

        if (actualSize < annotation.min() || actualSize > annotation.max()) {
            context.addIssue(annotation.message());
        }
    }
}
