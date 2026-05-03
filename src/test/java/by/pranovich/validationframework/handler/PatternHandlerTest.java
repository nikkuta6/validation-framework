package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Pattern;
import by.pranovich.validationframework.core.ValidationIssue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatternHandlerTest extends HandlerTestSupport {
    private final PatternHandler handler = new PatternHandler();

    @Test
    void shouldAddIssueWhenValueDoesNotMatchPattern() {
        CodeField target = new CodeField("abc");

        List<ValidationIssue> issues = validateField(handler, target, "code");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "code", "Code must match pattern");
    }

    @Test
    void shouldNotAddIssueWhenValueMatchesPattern() {
        CodeField target = new CodeField("ABC");

        List<ValidationIssue> issues = validateField(handler, target, "code");

        assertTrue(issues.isEmpty());
    }

    private static class CodeField {
        @Pattern(regex = "^[A-Z]{3}$", message = "Code must match pattern")
        private final String code;

        private CodeField(String code) {
            this.code = code;
        }
    }
}
