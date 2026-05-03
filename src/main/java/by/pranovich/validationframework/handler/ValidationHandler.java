package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.core.ValidationContext;

public abstract class ValidationHandler {
    private ValidationHandler next;

    public ValidationHandler linkWith(ValidationHandler next) {
        this.next = next;
        return next;
    }

    public void handle(ValidationContext context) {
        validate(context);

        if (next != null) {
            next.handle(context);
        }
    }

    protected abstract void validate(ValidationContext context);
}
