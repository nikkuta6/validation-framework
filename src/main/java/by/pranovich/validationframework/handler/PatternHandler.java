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

    if (!(value instanceof CharSequence text)) {
      issues
          .add(new ValidationIssue(field.getName(), "annotation @Pattern can be applied only to CharSequence fields"));
      return;
    }

    if (!text.toString().matches(annotation.regex())) {
      issues
          .add(new ValidationIssue(field.getName(), annotation.message()));
    }
  }
}
