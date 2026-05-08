package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.NotNull;
import by.pranovich.validationframework.core.ValidationIssue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotNullHandlerTest extends HandlerTestSupport {
    private final NotNullHandler handler = new NotNullHandler();

    @Test
    void shouldAddIssueWhenValueIsNull() {
        RequiredField target = new RequiredField(null);

        List<ValidationIssue> issues = validateField(handler, target, "name");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "name", "Name must not be null.");
    }

    @Test
    void shouldNotAddIssueWhenValueIsNotNull() {
        RequiredField target = new RequiredField("Nikita");

        List<ValidationIssue> issues = validateField(handler, target, "name");

        assertTrue(issues.isEmpty());
    }

    private static class RequiredField {
        @NotNull(message = "Name must not be null.")
        private final String name;

        private RequiredField(String name) {
            this.name = name;
        }
    }
}
