# Validation Framework

An educational Java framework for validating objects through field annotations.

The framework scans object fields with reflection, finds supported annotations, and returns validation errors as `ValidationIssue` objects. Internally, validation rules are organized as a Chain of Responsibility.

## Requirements

- Java 21
- Maven 3.8+

## Build And Installation

The project is built as a Maven `jar`:

```xml
<groupId>by.pranovich</groupId>
<artifactId>validation-framework</artifactId>
<version>1.0.0</version>
```

Build the library:

```bash
mvn clean package
```

Install it into the local Maven repository so it can be used from another project:

```bash
mvn clean install
```

Then add the dependency to the consumer project's `pom.xml`:

```xml
<dependency>
    <groupId>by.pranovich</groupId>
    <artifactId>validation-framework</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Quick Start

Create a model and add validation annotations to its fields:

```java
import by.pranovich.validationframework.annotation.Email;
import by.pranovich.validationframework.annotation.NotBlank;
import by.pranovich.validationframework.annotation.NotEmpty;
import by.pranovich.validationframework.annotation.Pattern;
import by.pranovich.validationframework.annotation.Range;
import by.pranovich.validationframework.annotation.Size;

import java.util.List;

public class User {
    @NotBlank(message = "Name must not be blank.")
    @Size(min = 2, max = 30, message = "Name length must be between 2 and 30 characters.")
    private final String name;

    @NotBlank(message = "Email must not be blank.")
    @Email(message = "Email must be valid.")
    private final String email;

    @Range(min = 18, max = 120, message = "Age must be between 18 and 120.")
    private final int age;

    @NotEmpty(message = "Roles must not be empty.")
    private final List<String> roles;

    @Pattern(regex = "^[A-Z]{2}-\\d{4}$", message = "Code has an invalid format.")
    private final String code;

    public User(String name, String email, int age, List<String> roles, String code) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.roles = roles;
        this.code = code;
    }
}
```

Run validation through the public API:

```java
import by.pranovich.validationframework.Validation;
import by.pranovich.validationframework.core.ValidationIssue;
import by.pranovich.validationframework.validator.Validator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user = new User(" ", "wrong-email", 15, List.of(), "bad-code");

        Validator validator = Validation.defaultValidator();
        List<ValidationIssue> issues = validator.validate(user);

        for (ValidationIssue issue : issues) {
            System.out.println(issue);
        }

        boolean valid = validator.isValid(user);
        System.out.println("Valid: " + valid);
    }
}
```

Example output:

```text
Field: name. Error: Name must not be blank.
Field: name. Error: Name length must be between 2 and 30 characters.
Field: email. Error: Email must be valid.
Field: age. Error: Age must be between 18 and 120.
Field: roles. Error: Roles must not be empty.
Field: code. Error: Code has an invalid format.
Valid: false
```

## Main API

### `Validation.defaultValidator()`

Creates a standard validator with all built-in handlers:

```java
Validator validator = Validation.defaultValidator();
```

### `Validator`

The main validation contract:

```java
List<ValidationIssue> validate(Object target);
boolean isValid(Object target);
```

`validate()` returns a list of validation errors. If the list is empty, the object is valid. `isValid()` is a convenience method built on top of `validate()`.

### `ValidationIssue`

Describes one validation error:

```java
String fieldName = issue.getFieldName();
String message = issue.getMessage();
```

`fieldName` contains the name of the field where the error was found. `message` contains the annotation message or a framework service message.

`toString()` returns a ready-to-print message:

```text
Field: name. Error: Value must not be null.
```

## Supported Annotations

All annotations are applied to fields and are available at runtime.

| Annotation | Supported type | Description |
| --- | --- | --- |
| `@NotNull` | any type | The field value must not be `null`. |
| `@NotBlank` | `CharSequence` | The value must not be `null`, empty, or whitespace-only. |
| `@NotEmpty` | `CharSequence`, array, `Collection`, `Map` | The value must not be `null` or empty. |
| `@Size` | `CharSequence`, array, `Collection`, `Map` | The size must be between `min` and `max`, inclusive. |
| `@Pattern` | `CharSequence` | The value must match a regular expression. |
| `@Email` | `String` | The value must be a string with a valid email-like format. |
| `@Range` | `Number` | The number must be between `min` and `max`, inclusive. |
| `@Min` | `Number` | The number must be greater than or equal to the configured minimum. |
| `@Max` | `Number` | The number must be less than or equal to the configured maximum. |
| `@Positive` | `Number` | The number must be strictly greater than zero. |
| `@Negative` | `Number` | The number must be strictly less than zero. |

## Annotation Parameters

### `@NotNull`

```java
@NotNull(message = "Value must not be null.")
```

| Parameter | Default | Description |
| --- | --- | --- |
| `message` | `"Value must not be null."` | Message used when the value is `null`. |

### `@NotBlank`

```java
@NotBlank(message = "Value must not be blank.")
```

| Parameter | Default | Description |
| --- | --- | --- |
| `message` | `"Value must not be blank."` | Message used when the value is `null`, empty, or whitespace-only. |

### `@NotEmpty`

```java
@NotEmpty(message = "Value must not be empty.")
```

| Parameter | Default | Description |
| --- | --- | --- |
| `message` | `"Value must not be empty."` | Message used when the value is `null` or has size `0`. |

### `@Size`

```java
@Size(min = 2, max = 10, message = "Value size must be within the allowed range.")
```

| Parameter | Default | Description |
| --- | --- | --- |
| `min` | `0` | Minimum allowed size. |
| `max` | `Integer.MAX_VALUE` | Maximum allowed size. |
| `message` | `"Value size must be within the allowed range."` | Message used when the size is outside the allowed range. |

If `min` or `max` is negative, or if `min > max`, the framework returns a service error about invalid annotation configuration.

### `@Pattern`

```java
@Pattern(regex = "^[A-Z]{3}$", message = "Value must match the regular expression.")
```

| Parameter | Default | Description |
| --- | --- | --- |
| `regex` | none | Regular expression used for validation. |
| `message` | `"Value must match the regular expression."` | Message used when the value does not match `regex`. |

### `@Email`

```java
@Email(message = "Value must be a valid email address.")
```

| Parameter | Default | Description |
| --- | --- | --- |
| `message` | `"Value must be a valid email address."` | Message used when the email is invalid. |

### `@Range`

```java
@Range(min = 18, max = 120, message = "Value must be within the allowed range.")
```

| Parameter | Default | Description |
| --- | --- | --- |
| `min` | none | Minimum allowed value. |
| `max` | none | Maximum allowed value. |
| `message` | `"Value must be within the allowed range."` | Message used when the number is less than `min` or greater than `max`. |

If `min > max`, the framework returns a service error about invalid annotation configuration.

### `@Min`

```java
@Min(value = 18, message = "Value must be greater than or equal to the minimum.")
```

| Parameter | Default | Description |
| --- | --- | --- |
| `value` | none | Minimum allowed value. |
| `message` | `"Value must be greater than or equal to the minimum."` | Message used when the number is less than the minimum. |

### `@Max`

```java
@Max(value = 100, message = "Value must be less than or equal to the maximum.")
```

| Parameter | Default | Description |
| --- | --- | --- |
| `value` | none | Maximum allowed value. |
| `message` | `"Value must be less than or equal to the maximum."` | Message used when the number is greater than the maximum. |

### `@Positive`

```java
@Positive(message = "Value must be positive.")
```

| Parameter | Default | Description |
| --- | --- | --- |
| `message` | `"Value must be positive."` | Message used when the number is less than or equal to zero. |

### `@Negative`

```java
@Negative(message = "Value must be negative.")
```

| Parameter | Default | Description |
| --- | --- | --- |
| `message` | `"Value must be negative."` | Message used when the number is greater than or equal to zero. |

## `null` Behavior

`@NotNull`, `@NotBlank`, and `@NotEmpty` treat `null` as an error.

Other annotations skip `null`, so they can be combined with required-value checks:

```java
@NotBlank
@Size(min = 2, max = 30)
private String name;
```

If `name == null`, `@NotBlank` adds an error. `@Size` does not add an additional error.

If `validator.validate(null)` receives the target object itself as `null`, the method returns one error:

```text
fieldName = "object"
message = "Validated object must not be null."
```

## Internal Structure

Main packages:

```text
by.pranovich.validationframework
|-- annotation   # Validation annotations
|-- core         # ValidationIssue and ValidationContext
|-- exception    # ValidationException
|-- factory      # Default handler chain construction
|-- handler      # Annotation handlers
|-- util         # Utility classes
`-- validator    # Validator interface and ObjectFieldValidator
```

Main classes:

| Class | Responsibility |
| --- | --- |
| `Validation` | Public entry point that creates the standard validator. |
| `Validator` | Interface for running validation. |
| `ObjectFieldValidator` | Scans object fields and runs the handler chain. |
| `ValidationHandler` | Base class for handlers in the chain. |
| `ValidationHandlerChainFactory` | Creates the standard handler chain. |
| `ValidationIssue` | Describes one validation error. |
| `ValidationContext` | Passes the field, target object, and shared error list to handlers. |
| `ValueSizeResolver` | Resolves sizes for strings, arrays, collections, and maps. |
| `ValidationException` | Runtime exception for internal framework errors. |

## Validation Flow

1. `Validation.defaultValidator()` creates an `ObjectFieldValidator`.
2. `ObjectFieldValidator` gets fields through reflection.
3. A `ValidationContext` is created for each field.
4. The `ValidationHandler` chain processes the field.
5. Found errors are added to the shared `List<ValidationIssue>` through `context.addIssue(...)`.

Default handler order:

```text
NotNull -> NotBlank -> NotEmpty -> Pattern -> Email -> Size -> Range -> Max -> Min -> Positive -> Negative
```

## Manual Validator Creation

Usually `Validation.defaultValidator()` is enough. To create a validator explicitly:

```java
import by.pranovich.validationframework.factory.ValidationHandlerChainFactory;
import by.pranovich.validationframework.handler.ValidationHandler;
import by.pranovich.validationframework.validator.ObjectFieldValidator;
import by.pranovich.validationframework.validator.Validator;

ValidationHandler chain = ValidationHandlerChainFactory.createDefaultChain();
Validator validator = new ObjectFieldValidator(chain);
```

## Adding A New Annotation

To add a new validation rule:

1. Create an annotation in `by.pranovich.validationframework.annotation`.
2. Add `@Retention(RetentionPolicy.RUNTIME)` and `@Target(ElementType.FIELD)`.
3. Create a handler in `by.pranovich.validationframework.handler`.
4. Extend `ValidationHandler`.
5. In `validate(ValidationContext context)`, check the annotation with `context.hasAnnotation(...)`.
6. Read the field value with `context.getValue()` and add errors with `context.addIssue(message)`.
7. Register the new handler in `ValidationHandlerChainFactory`.

Minimal handler example:

```java
import by.pranovich.validationframework.core.ValidationContext;

public class CustomHandler extends ValidationHandler {
    @Override
    protected void validate(ValidationContext context) {
        if (!context.hasAnnotation(Custom.class)) {
            return;
        }

        Object value = context.getValue();
        Custom annotation = context.getAnnotation(Custom.class);

        if (value == null) {
            return;
        }

        if (/* value is invalid */) {
            context.addIssue(annotation.message());
        }
    }
}
```

## Current Limitations

- Only fields declared directly in the target class are validated: `target.getClass().getDeclaredFields()`.
- Parent class fields are not scanned yet.
- `static` fields are not skipped automatically yet.
- Numeric handlers compare values through `doubleValue()`, so `BigDecimal`, `BigInteger`, `NaN`, and infinities may have edge cases.
- `@Email` uses a simplified regex and does not cover the full email standard.
- Method, parameter, class-level constraint, and nested-object validation are not implemented yet.

## Tests

Tests are stored in the standard Maven directory `src/test/java`. Test packages mirror the production package structure from `src/main/java`, so tests are easy to find next to the classes they cover:

```text
src/test/java/by/pranovich/validationframework/ValidationTest.java
src/test/java/by/pranovich/validationframework/handler/NotEmptyHandlerTest.java
src/test/java/by/pranovich/validationframework/handler/SizeHandlerTest.java
src/test/java/by/pranovich/validationframework/validator/ObjectFieldValidatorTest.java
```

Coverage map:

| Area | Test location |
| --- | --- |
| Public `Validation` facade | `src/test/java/by/pranovich/validationframework/ValidationTest.java` |
| Handlers from the `handler` package | `src/test/java/by/pranovich/validationframework/handler/*HandlerTest.java` |
| Validators from the `validator` package | `src/test/java/by/pranovich/validationframework/validator/*Test.java` |

Test names describe expected behavior in the `should...When...` format, for example `shouldAddIssueWhenStringIsEmpty`.

Shared handler test setup lives in `HandlerTestSupport`: it creates a `ValidationContext`, runs one handler, and returns a list of `ValidationIssue` objects.

Run compilation without tests:

```bash
mvn clean compile -DskipTests
```

Run all tests:

```bash
mvn clean test
```

Run one test class:

```bash
mvn -Dtest=SizeHandlerTest test
```
