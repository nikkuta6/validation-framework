package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Negative;
import by.pranovich.validationframework.core.ValidationIssue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NegativeHandlerTest extends HandlerTestSupport {
    private final NegativeHandler handler = new NegativeHandler();

    @Test
    void shouldAddIssueWhenNumberIsZero() {
        NegativeNumberField target = new NegativeNumberField(0);

        List<ValidationIssue> issues = validateField(handler, target, "number");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "number", "Number must be negative");
    }

    @Test
    void shouldNotAddIssueWhenNumberIsNegative() {
        NegativeNumberField target = new NegativeNumberField(-1);

        List<ValidationIssue> issues = validateField(handler, target, "number");

        assertTrue(issues.isEmpty());
    }

    private static class NegativeNumberField {
        @Negative(message = "Number must be negative")
        private final int number;

        private NegativeNumberField(int number) {
            this.number = number;
        }
    }
}
