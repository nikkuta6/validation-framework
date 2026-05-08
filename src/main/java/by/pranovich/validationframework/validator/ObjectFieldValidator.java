package by.pranovich.validationframework.validator;

import by.pranovich.validationframework.core.ValidationContext;
import by.pranovich.validationframework.core.ValidationIssue;
import by.pranovich.validationframework.handler.ValidationHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectFieldValidator implements Validator {
    private final ValidationHandler handlerChain;

    public ObjectFieldValidator(ValidationHandler handlerChain) {
        this.handlerChain = Objects.requireNonNull(handlerChain, "Handler chain must not be null.");
    }

    @Override
    public List<ValidationIssue> validate(Object target) {
        List<ValidationIssue> issues = new ArrayList<>();

        if (target == null) {
            issues.add(new ValidationIssue("object", "Validated object must not be null."));
            return issues;
        }

        Field[] fields = target.getClass().getDeclaredFields();

        for (Field field : fields) {
            ValidationContext context = new ValidationContext(field, target, issues);
            handlerChain.handle(context);
        }

        return issues;
    }
}
