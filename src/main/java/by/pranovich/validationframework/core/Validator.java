package by.pranovich.validationframework.core;

import by.pranovich.validationframework.handlers.AbstractValidationHandler;
import by.pranovich.validationframework.handlers.NotNullHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Validator {
    private final AbstractValidationHandler chain;

    public Validator() {
        this.chain = buildChain();
    }

    private AbstractValidationHandler buildChain() {
        return new NotNullHandler();
    }

    public List<ValidationError> validate(Object object) {
        List<ValidationError> errors = new ArrayList<>();

        if (object == null) {
            errors.add(new ValidationError("object", "Validated object is null!"));
        }

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object value = field.get(object);
                ValidationContext context = new ValidationContext(object, field, value, errors);
                chain.handle(context);
            } catch (IllegalAccessException e) {
                errors.add(new ValidationError(field.getName(), "Access error: " + e.getMessage()));
            }
        }

        return errors;
    }

    public boolean isValid(Object object) {
        return validate(object).isEmpty();
    }

}
