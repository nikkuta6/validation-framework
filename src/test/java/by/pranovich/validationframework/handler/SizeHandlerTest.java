package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Size;
import by.pranovich.validationframework.core.ValidationIssue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SizeHandlerTest extends HandlerTestSupport {
    private final SizeHandler handler = new SizeHandler();

    @Test
    void shouldAddIssueWhenSizeIsOutOfRange() {
        NameField target = new NameField("A");

        List<ValidationIssue> issues = validateField(handler, target, "name");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "name", "name length must be between 2 and 4");
    }

    @Test
    void shouldNotAddIssueWhenSizeIsWithinRange() {
        NameField target = new NameField("Alex");

        List<ValidationIssue> issues = validateField(handler, target, "name");

        assertTrue(issues.isEmpty());
    }

    private static class NameField {
        @Size(min = 2, max = 4, message = "name length must be between 2 and 4")
        private final String name;

        private NameField(String name) {
            this.name = name;
        }
    }
}
