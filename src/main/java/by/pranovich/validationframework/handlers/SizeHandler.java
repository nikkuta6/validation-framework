package by.pranovich.validationframework.handlers;

import java.lang.reflect.Field;
import java.util.List;

import by.pranovich.validationframework.annotations.Size;
import by.pranovich.validationframework.core.ValidationError;

public class SizeHandler extends ValidationHandler {

  @Override
  protected void validate(Field field, Object target, List<ValidationError> errors) {
    if (!field.isAnnotationPresent(Size.class)) {
      return;
    }

    Object value = getFieldValue(field, target);
    Size annotation = field.getAnnotation(Size.class);

    if (!(value instanceof Number number)) {
      errors.add(new ValidationError(field.getName(), "Annotation @Size can be applied only to numeric fields"));
      return;
    }

    // if (number.doubleValue() !== annotation.value()) {
    // errors.add(new ValidationError(field.getName(), annotation.message()));
    // }
  }
}
