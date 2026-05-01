package by.pranovich.validationframework.exception;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable reason) {
        super(reason);
    }

    public ValidationException(String message, Throwable reason) {
        super(message, reason);
    }
}
