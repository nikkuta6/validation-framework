package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.NotEmpty;
import by.pranovich.validationframework.core.ValidationIssue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotEmptyHandlerTest extends HandlerTestSupport {
    private final NotEmptyHandler handler = new NotEmptyHandler();

    @Test
    void shouldAddIssueWhenValueIsNull() {
        TextField target = new TextField(null);

        List<ValidationIssue> issues = validateField(handler, target, "name");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "name", "Name must not be empty.");
    }

    @Test
    void shouldAddIssueWhenStringIsEmpty() {
        TextField target = new TextField("");

        List<ValidationIssue> issues = validateField(handler, target, "name");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "name", "Name must not be empty.");
    }

    @Test
    void shouldAddIssueWhenCollectionIsEmpty() {
        TagsField target = new TagsField(List.of());

        List<ValidationIssue> issues = validateField(handler, target, "tags");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "tags", "Tags must not be empty.");
    }

    @Test
    void shouldNotAddIssueWhenArrayIsNotEmpty() {
        CodesField target = new CodesField(new String[] {"A1"});

        List<ValidationIssue> issues = validateField(handler, target, "codes");

        assertTrue(issues.isEmpty());
    }

    @Test
    void shouldAddIssueWhenValueHasUnsupportedType() {
        NumberField target = new NumberField(1);

        List<ValidationIssue> issues = validateField(handler, target, "amount");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "amount", "Annotation @NotEmpty can be applied only to CharSequence, array, Collection, or Map fields.");
    }

    private static class TextField {
        @NotEmpty(message = "Name must not be empty.")
        private final String name;

        private TextField(String name) {
            this.name = name;
        }
    }

    private static class TagsField {
        @NotEmpty(message = "Tags must not be empty.")
        private final List<String> tags;

        private TagsField(List<String> tags) {
            this.tags = tags;
        }
    }

    private static class CodesField {
        @NotEmpty
        private final String[] codes;

        private CodesField(String[] codes) {
            this.codes = codes;
        }
    }

    private static class NumberField {
        @NotEmpty
        private final int amount;

        private NumberField(int amount) {
            this.amount = amount;
        }
    }
}
