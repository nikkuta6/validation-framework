package by.pranovich.validationframework.handler;

import java.lang.reflect.Field;
import java.util.List;

import by.pranovich.validationframework.annotation.Negative;
import by.pranovich.validationframework.core.ValidationIssue;

public class NegativeHandler extends ValidationHandler {
  @Override
  protected void validate(Field field, Object target, List<ValidationIssue> issues) {
    if (!field.isAnnotationPresent(Negative.class)) {
      return;
    }

    Object value = getFieldValue(field, target);
    Negative annotation = field.getAnnotation(Negative.class);

    if (value == null) {
      return;
    }

    if (!(value instanceof Number number)) {
      issues.add(new ValidationIssue(field.getName(), "Field must be a number!"));
      return;
    }

    if (number.doubleValue() >= 0) {
      issues.add(new ValidationIssue(field.getName(), annotation.message()));
    }
  }
}
