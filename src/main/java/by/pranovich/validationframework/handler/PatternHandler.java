package by.pranovich.validationframework.handler;

import java.lang.reflect.Field;
import java.util.List;

import by.pranovich.validationframework.annotation.Pattern;
import by.pranovich.validationframework.core.ValidationIssue;

public class PatternHandler extends ValidationHandler {

  @Override
  protected void validate(Field field, Object target, List<ValidationIssue> issues) {
    if (!field.isAnnotationPresent(Pattern.class)) {
      return;
    }

    Object value = getFieldValue(field, target);
    Pattern annotation = field.getAnnotation(Pattern.class);

    if (value == null) {
      return;
    }

    if (!(value instanceof String)) {
      issues.add(new ValidationIssue(field.getName(), "Annotation @Pattern can be applied only to String fields"));
      return;
    }

    if (!value.toString().matches(annotation.regex())) {
      issues
          .add(new ValidationIssue(field.getName(), annotation.message() + annotation.regex()));
    }
  }
}
