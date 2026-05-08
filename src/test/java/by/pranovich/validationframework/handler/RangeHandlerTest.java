package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Range;
import by.pranovich.validationframework.core.ValidationIssue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RangeHandlerTest extends HandlerTestSupport {
    private final RangeHandler handler = new RangeHandler();

    @Test
    void shouldAddIssueWhenNumberIsLessThanMinimum() {
        AgeField target = new AgeField(17);

        List<ValidationIssue> issues = validateField(handler, target, "age");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "age", "Age must be between 18 and 120.");
    }

    @Test
    void shouldAddIssueWhenNumberIsGreaterThanMaximum() {
        AgeField target = new AgeField(121);

        List<ValidationIssue> issues = validateField(handler, target, "age");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "age", "Age must be between 18 and 120.");
    }

    @Test
    void shouldNotAddIssueWhenNumberIsWithinRange() {
        AgeField target = new AgeField(18);

        List<ValidationIssue> issues = validateField(handler, target, "age");

        assertTrue(issues.isEmpty());
    }

    @Test
    void shouldNotAddIssueWhenValueIsNull() {
        NullableScoreField target = new NullableScoreField(null);

        List<ValidationIssue> issues = validateField(handler, target, "score");

        assertTrue(issues.isEmpty());
    }

    @Test
    void shouldAddIssueWhenValueIsNotNumeric() {
        TextField target = new TextField("A");

        List<ValidationIssue> issues = validateField(handler, target, "text");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "text", "Annotation @Range can be applied only to numeric fields.");
    }

    @Test
    void shouldAddIssueWhenAnnotationParametersAreInvalid() {
        InvalidRangeField target = new InvalidRangeField(7);

        List<ValidationIssue> issues = validateField(handler, target, "value");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "value", "Annotation @Range has invalid parameters: min must be less than or equal to max.");
    }

    private static class AgeField {
        @Range(min = 18, max = 120, message = "Age must be between 18 and 120.")
        private final int age;

        private AgeField(int age) {
            this.age = age;
        }
    }

    private static class NullableScoreField {
        @Range(min = 0, max = 100)
        private final Integer score;

        private NullableScoreField(Integer score) {
            this.score = score;
        }
    }

    private static class TextField {
        @Range(min = 1, max = 5)
        private final String text;

        private TextField(String text) {
            this.text = text;
        }
    }

    private static class InvalidRangeField {
        @Range(min = 10, max = 5)
        private final int value;

        private InvalidRangeField(int value) {
            this.value = value;
        }
    }
}
