package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Email;
import by.pranovich.validationframework.core.ValidationIssue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailHandlerTest extends HandlerTestSupport {
    private final EmailHandler handler = new EmailHandler();

    @Test
    void shouldAddIssueWhenEmailIsInvalid() {
        EmailField target = new EmailField("wrong-email");

        List<ValidationIssue> issues = validateField(handler, target, "email");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "email", "email is invalid");
    }

    @Test
    void shouldNotAddIssueWhenEmailIsValid() {
        EmailField target = new EmailField("nikita@example.com");

        List<ValidationIssue> issues = validateField(handler, target, "email");

        assertTrue(issues.isEmpty());
    }

    private static class EmailField {
        @Email(message = "email is invalid")
        private final String email;

        private EmailField(String email) {
            this.email = email;
        }
    }
}
