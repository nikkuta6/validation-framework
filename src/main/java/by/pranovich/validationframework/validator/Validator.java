package by.pranovich.validationframework.validator;


import by.pranovich.validationframework.core.ValidationError;
import by.pranovich.validationframework.handlers.ValidationHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Validator {
    private final ValidationHandler handlerChain;

    public Validator(ValidationHandler handlerChain) {
        this.handlerChain = Objects.requireNonNull(handlerChain, "handlerChain must not be null");
    }

    public List<ValidationError> validate(Object target) {
        List<ValidationError> errors = new ArrayList<>();

        if (target == null) {
            errors.add(new ValidationError("object", "Validated object is null!"));
            return errors;
        }

        Field[] fields = target.getClass().getDeclaredFields();

        for (Field field : fields) {
            handlerChain.handle(field, target, errors);
        }

        return errors;
    }
}
