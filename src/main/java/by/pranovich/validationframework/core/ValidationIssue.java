package by.pranovich.validationframework.core;

public class ValidationIssue {
    private final String fieldName;
    private final String message;

    public ValidationIssue(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Field: ").append(fieldName).append(", error: ").append(message);
        return sb.toString();
    }
}
