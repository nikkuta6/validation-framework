package by.pranovich.validationframework.handler;

import java.util.OptionalInt;

import by.pranovich.validationframework.annotation.NotEmpty;
import by.pranovich.validationframework.core.ValidationContext;
import by.pranovich.validationframework.util.ValueSizeResolver;

public class NotEmptyHandler extends ValidationHandler {

    @Override
    protected void validate(ValidationContext context) {
        if (!context.hasAnnotation(NotEmpty.class)) {
            return;
        }

        Object value = context.getValue();
        NotEmpty annotation = context.getAnnotation(NotEmpty.class);

        if (value == null) {
            context.addIssue(annotation.message());
            return;
        }

        OptionalInt size = ValueSizeResolver.getSize(value);
        if (size.isEmpty()) {
            context.addIssue("Annotation @NotEmpty can be applied only to CharSequence, array, Collection, or Map fields.");
            return;
        }

        if (size.getAsInt() == 0) {
            context.addIssue(annotation.message());
        }
    }
}
