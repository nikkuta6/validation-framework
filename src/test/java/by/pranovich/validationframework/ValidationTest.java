package by.pranovich.validationframework;

import by.pranovich.validationframework.annotation.NotBlank;
import by.pranovich.validationframework.annotation.NotEmpty;
import by.pranovich.validationframework.annotation.NotNull;
import by.pranovich.validationframework.annotation.Range;
import by.pranovich.validationframework.core.ValidationIssue;
import by.pranovich.validationframework.validator.Validator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationTest {

    @Test
    void defaultValidatorShouldValidateAnnotatedObjectThroughPublicApi() {
        Validator validator = Validation.defaultValidator();

        List<ValidationIssue> issues = validator.validate(new SampleUser(null));

        assertEquals(1, issues.size());
        assertEquals("name", issues.get(0).getFieldName());
        assertEquals("field must not be null", issues.get(0).getMessage());
        assertFalse(validator.isValid(new SampleUser(null)));
    }

    @Test
    void defaultValidatorShouldValidateNotBlankNotEmptyAndRangeAnnotations() {
        Validator validator = Validation.defaultValidator();

        List<ValidationIssue> issues = validator.validate(new UserWithSeveralInvalidFields(" ", List.of(), 15));

        assertEquals(3, issues.size());
        assertHasIssue(issues, "name", "name must not be blank");
        assertHasIssue(issues, "roles", "roles must not be empty");
        assertHasIssue(issues, "age", "age must be between 18 and 120");
    }

    private static void assertHasIssue(List<ValidationIssue> issues, String fieldName, String message) {
        assertTrue(
                issues.stream().anyMatch(issue ->
                        issue.getFieldName().equals(fieldName) && issue.getMessage().equals(message)),
                "expected issue for field '" + fieldName + "' with message '" + message + "'");
    }

    private static class SampleUser {
        @NotNull
        private final String name;

        private SampleUser(String name) {
            this.name = name;
        }
    }

    private static class UserWithSeveralInvalidFields {
        @NotBlank(message = "name must not be blank")
        private final String name;

        @NotEmpty(message = "roles must not be empty")
        private final List<String> roles;

        @Range(min = 18, max = 120, message = "age must be between 18 and 120")
        private final int age;

        private UserWithSeveralInvalidFields(String name, List<String> roles, int age) {
            this.name = name;
            this.roles = roles;
            this.age = age;
        }
    }
}
