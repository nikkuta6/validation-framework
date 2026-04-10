package by.pranovich.validationframework.handlers;

import java.lang.reflect.Field;
import java.util.List;

import by.pranovich.validationframework.annotations.Min;
import by.pranovich.validationframework.core.ValidationError;

public class MinHandler extends ValidationHandler {

  @Override
  protected void validate(Field field, Object target, List<ValidationError> errors) {
    if (!field.isAnnotationPresent(Min.class)) {
      return;
    }

    Object value = getFieldValue(field, target);
    Min annotation = field.getAnnotation(Min.class);

    if (value == null) {
      return;
    }

    if (!(value instanceof Number number)) {
      errors.add(new ValidationError(field.getName(), "Annotation @Min can be applied only to numeric fields"));
      return;
    }

    if (number.doubleValue() < annotation.value()) {
      errors.add(new ValidationError(field.getName(), annotation.message()));
    }
  }
}
