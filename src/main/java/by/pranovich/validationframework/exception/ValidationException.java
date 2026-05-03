package by.pranovich.validationframework.exception;

public class ValidationException extends RuntimeException {
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
