package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Positive;
import by.pranovich.validationframework.core.ValidationIssue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PositiveHandlerTest extends HandlerTestSupport {
    private final PositiveHandler handler = new PositiveHandler();

    @Test
    void shouldAddIssueWhenNumberIsZero() {
        PositiveNumberField target = new PositiveNumberField(0);

        List<ValidationIssue> issues = validateField(handler, target, "number");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "number", "Number must be positive.");
    }

    @Test
    void shouldNotAddIssueWhenNumberIsPositive() {
        PositiveNumberField target = new PositiveNumberField(1);

        List<ValidationIssue> issues = validateField(handler, target, "number");

        assertTrue(issues.isEmpty());
    }

    private static class PositiveNumberField {
        @Positive(message = "Number must be positive.")
        private final int number;

        private PositiveNumberField(int number) {
            this.number = number;
        }
    }
}
