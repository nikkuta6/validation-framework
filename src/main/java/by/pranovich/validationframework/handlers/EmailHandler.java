package by.pranovich.validationframework.handlers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;

import by.pranovich.validationframework.annotations.Email;
import by.pranovich.validationframework.core.ValidationError;

public class EmailHandler extends ValidationHandler {
  private static final Pattern VALID_EMAIL_REGEX = Pattern.compile(
      "^[a-z0-9._-]{3,20}@[a-z0-9.-]{3,20}\\.[a-z]{2,5}$");

  @Override
  protected void validate(Field field, Object target, List<ValidationError> errors) {
    if (!field.isAnnotationPresent(Email.class)) {
      return;
    }

    Object value = getFieldValue(field, target);
    Email annotation = field.getAnnotation(Email.class);

    if (value == null) {
      return;
    }

    if (!(value instanceof String email)) {
      errors.add(new ValidationError(field.getName(), "Annotation @Email can be applied only to string fields"));
      return;
    }

    if (!VALID_EMAIL_REGEX.matcher(email).matches()) {
      errors.add(new ValidationError(field.getName(), annotation.message()));
    }
  }
}
