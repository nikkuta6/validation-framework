package by.pranovich.validationframework;

import by.pranovich.validationframework.annotation.NotNull;
import by.pranovich.validationframework.core.ValidationIssue;
import by.pranovich.validationframework.validator.Validator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    private static class SampleUser {
        @NotNull
        private final String name;

        private SampleUser(String name) {
            this.name = name;
        }
    }
}
