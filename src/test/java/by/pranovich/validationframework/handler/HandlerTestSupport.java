package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.core.ValidationIssue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class HandlerTestSupport {

    protected List<ValidationIssue> validateField(ValidationHandler handler, Object target, String fieldName) {
        List<ValidationIssue> issues = new ArrayList<>();
        handler.handle(getField(target, fieldName), target, issues);
        return issues;
    }

    protected static void assertHasIssue(List<ValidationIssue> issues, String fieldName, String message) {
        assertTrue(
                issues.stream().anyMatch(issue ->
                        issue.getFieldName().equals(fieldName) && issue.getMessage().equals(message)),
                "Expected issue for field '" + fieldName + "' with message '" + message + "'");
    }

    private static Field getField(Object target, String fieldName) {
        try {
            return target.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Field not found: " + fieldName, e);
        }
    }
}
