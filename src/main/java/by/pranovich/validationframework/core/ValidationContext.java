package by.pranovich.validationframework.core;

import by.pranovich.validationframework.exception.ValidationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class ValidationContext {
    private final Field field;
    private final Object target;
    private final List<ValidationIssue> issues;

    public ValidationContext(Field field, Object target, List<ValidationIssue> issues) {
        this.field = field;
        this.target = target;
        this.issues = issues;
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
        return field.isAnnotationPresent(annotationClass);
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
        return field.getAnnotation(annotationClass);
    }

    public Object getValue() {
        field.setAccessible(true);
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new ValidationException("Unable to access field: " + field.getName(), e);
        }
    }

    public void addIssue(String message) {
        issues.add(new ValidationIssue(field.getName(), message));
    }
}
