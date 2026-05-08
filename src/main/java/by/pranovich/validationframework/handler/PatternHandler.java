package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Pattern;
import by.pranovich.validationframework.core.ValidationContext;

public class PatternHandler extends ValidationHandler {

    @Override
    protected void validate(ValidationContext context) {
        if (!context.hasAnnotation(Pattern.class)) {
            return;
        }

        Object value = context.getValue();
        Pattern annotation = context.getAnnotation(Pattern.class);

        if (value == null) {
            return;
        }

        if (!(value instanceof CharSequence text)) {
            context.addIssue("Annotation @Pattern can be applied only to CharSequence fields.");
            return;
        }

        if (!text.toString().matches(annotation.regex())) {
            context.addIssue(annotation.message());
        }
    }
}
