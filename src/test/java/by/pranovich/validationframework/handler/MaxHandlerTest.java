package by.pranovich.validationframework.handler;

import by.pranovich.validationframework.annotation.Max;
import by.pranovich.validationframework.core.ValidationIssue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaxHandlerTest extends HandlerTestSupport {
    private final MaxHandler handler = new MaxHandler();

    @Test
    void shouldAddIssueWhenNumberIsGreaterThanMaximum() {
        ScoreField target = new ScoreField(101);

        List<ValidationIssue> issues = validateField(handler, target, "score");

        assertEquals(1, issues.size());
        assertHasIssue(issues, "score", "score must be at most 100");
    }

    @Test
    void shouldNotAddIssueWhenNumberEqualsMaximum() {
        ScoreField target = new ScoreField(100);

        List<ValidationIssue> issues = validateField(handler, target, "score");

        assertTrue(issues.isEmpty());
    }

    private static class ScoreField {
        @Max(value = 100, message = "score must be at most 100")
        private final int score;

        private ScoreField(int score) {
            this.score = score;
        }
    }
}
