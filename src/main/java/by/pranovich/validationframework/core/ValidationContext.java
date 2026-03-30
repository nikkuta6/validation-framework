package by.pranovich.validationframework.core;

import java.lang.reflect.Field;
import java.util.List;

public class ValidationContext {
    private final Object targetObject;
    private final Field field;
    private final Object fieldValue;
    private final List<ValidationError> errors;

    public ValidationContext(Object targetObject, Field field, Object fieldValue, List<ValidationError> errors) {
        this.targetObject = targetObject;
        this.field = field;
        this.fieldValue = fieldValue;
        this.errors = errors;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Field getField() {
        return field;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
