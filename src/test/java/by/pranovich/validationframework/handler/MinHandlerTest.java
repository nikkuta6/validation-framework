package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Min;
import by.pranovich.validationframework.core.ValidationIssue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinHandlerTest extends HandlerTestSupport {
    private final MinHandler handler = new MinHandler();

    @Test
    void shouldAddIssueWhenNumberIsLessThanMinimum() {
        AgeField target = new AgeField(17);

        List<ValidationIssue> issues = validateField(handler, target, "age");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "age", "Age must be at least 18.");
    }

    @Test
    void shouldNotAddIssueWhenNumberEqualsMinimum() {
        AgeField target = new AgeField(18);

        List<ValidationIssue> issues = validateField(handler, target, "age");

        assertTrue(issues.isEmpty());
    }

    private static class AgeField {
        @Min(value = 18, message = "Age must be at least 18.")
        private final int age;

        private AgeField(int age) {
            this.age = age;
        }
    }
}
