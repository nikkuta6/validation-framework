package by.pranovich.validationframework.validator;

import by.pranovich.validationframework.core.ValidationIssue;

import java.util.List;

public interface Validator {
    List<ValidationIssue> validate(Object target);

    default boolean isValid(Object target) {
        return validate(target).isEmpty();
    }
}
