package by.pranovich.validationframework.validator;

import by.pranovich.validationframework.core.ValidationIssue;
import by.pranovich.validationframework.handler.ValidationHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectFieldValidator {
    private final ValidationHandler handlerChain;

    public ObjectFieldValidator(ValidationHandler handlerChain) {
        this.handlerChain = Objects.requireNonNull(handlerChain, "handlerChain must not be null");
    }

    public List<ValidationIssue> validate(Object target) {
        List<ValidationIssue> issues = new ArrayList<>();

        if (target == null) {
            issues.add(new ValidationIssue("object", "Validated object is null!"));
            return issues;
        }

        Field[] fields = target.getClass().getDeclaredFields();

        for (Field field : fields) {
            handlerChain.handle(field, target, issues);
        }

        return issues;
    }
}
