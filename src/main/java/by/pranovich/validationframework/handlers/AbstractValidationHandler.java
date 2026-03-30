package by.pranovich.validationframework.handlers;

import by.pranovich.validationframework.core.ValidationContext;

public abstract class AbstractValidationHandler {
    private AbstractValidationHandler next;

    public AbstractValidationHandler setNext(AbstractValidationHandler next) {
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
