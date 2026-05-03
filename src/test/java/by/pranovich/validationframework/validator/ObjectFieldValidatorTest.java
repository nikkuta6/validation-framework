package by.pranovich.validationframework.validator;

import by.pranovich.validationframework.core.ValidationIssue;
import by.pranovich.validationframework.handler.NotNullHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectFieldValidatorTest {

    @Test
    void shouldReturnIssueWhenTargetIsNull() {
        ObjectFieldValidator validator = new ObjectFieldValidator(new NotNullHandler());

        List<ValidationIssue> issues = validator.validate(null);

        assertEquals(1, issues.size());
        assertHasIssue(issues, "object", "validated object is null!");
    }

    @Test
    void shouldThrowExceptionWhenHandlerChainIsNull() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new ObjectFieldValidator(null));

        assertEquals("handlerChain must not be null", exception.getMessage());
    }

    private static void assertHasIssue(List<ValidationIssue> issues, String fieldName, String message) {
        assertTrue(
                issues.stream().anyMatch(issue ->
                        issue.getFieldName().equals(fieldName) && issue.getMessage().equals(message)),
                "Expected issue for field '" + fieldName + "' with message '" + message + "'");
    }
}
