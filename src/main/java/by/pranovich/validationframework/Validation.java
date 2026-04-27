package by.pranovich.validationframework;

import by.pranovich.validationframework.factory.ValidationHandlerChainFactory;
import by.pranovich.validationframework.handler.ValidationHandler;
import by.pranovich.validationframework.validator.ObjectFieldValidator;
import by.pranovich.validationframework.validator.Validator;

public final class Validation {
    private Validation() {
    }

    public static Validator defaultValidator() {
        ValidationHandler chain = ValidationHandlerChainFactory.createDefaultChain();
        return new ObjectFieldValidator(chain);
    }
}
