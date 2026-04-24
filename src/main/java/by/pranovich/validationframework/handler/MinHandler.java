package by.pranovich.validationframework.handler;

import java.lang.reflect.Field;
import java.util.List;

import by.pranovich.validationframework.annotation.Min;
import by.pranovich.validationframework.core.ValidationIssue;

public class MinHandler extends ValidationHandler {

  @Override
  protected void validate(Field field, Object target, List<ValidationIssue> issues) {
    if (!field.isAnnotationPresent(Min.class)) {
      return;
    }

    Object value = getFieldValue(field, target);
    Min annotation = field.getAnnotation(Min.class);

    if (value == null) {
      return;
    }

    if (!(value instanceof Number number)) {
      issues.add(new ValidationIssue(field.getName(), "Annotation @Min can be applied only to numeric fields"));
      return;
    }

    if (number.doubleValue() < annotation.value()) {
      issues.add(new ValidationIssue(field.getName(), annotation.message()));
    }
  }
}
