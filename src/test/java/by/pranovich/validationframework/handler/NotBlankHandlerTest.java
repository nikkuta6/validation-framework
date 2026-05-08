package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.NotBlank;
import by.pranovich.validationframework.core.ValidationIssue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotBlankHandlerTest extends HandlerTestSupport {
    private final NotBlankHandler handler = new NotBlankHandler();

    @Test
    void shouldAddIssueWhenValueIsNull() {
        TextField target = new TextField(null);

        List<ValidationIssue> issues = validateField(handler, target, "name");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "name", "Name must not be blank.");
    }

    @Test
    void shouldAddIssueWhenStringIsBlank() {
        TextField target = new TextField("   ");

        List<ValidationIssue> issues = validateField(handler, target, "name");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "name", "Name must not be blank.");
    }

    @Test
    void shouldNotAddIssueWhenStringHasNonWhitespaceCharacters() {
        TextField target = new TextField(" Nikita ");

        List<ValidationIssue> issues = validateField(handler, target, "name");

        assertTrue(issues.isEmpty());
    }

    @Test
    void shouldAddIssueWhenValueIsNotCharSequence() {
        NumberField target = new NumberField(1);

        List<ValidationIssue> issues = validateField(handler, target, "amount");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "amount", "Annotation @NotBlank can be applied only to CharSequence fields.");
    }

    private static class TextField {
        @NotBlank(message = "Name must not be blank.")
        private final String name;

        private TextField(String name) {
            this.name = name;
        }
    }

    private static class NumberField {
        @NotBlank
        private final int amount;

        private NumberField(int amount) {
            this.amount = amount;
        }
    }
}
