# Validation Framework

Учебный Java-фреймворк для валидации объектов через аннотации на полях.

Фреймворк обходит поля объекта с помощью reflection, находит поддерживаемые аннотации и возвращает список ошибок валидации в виде `ValidationIssue`. Внутри обработчики правил собраны в цепочку по паттерну Chain of Responsibility.

## Требования

- Java 21
- Maven 3.8+

## Установка

Проект собирается как Maven `jar`:

```xml
<groupId>by.pranovich</groupId>
<artifactId>validation-framework</artifactId>
<version>1.0.0</version>
```

Для локальной сборки:

```bash
mvn clean package
```

## Быстрый Старт

Опишите модель и добавьте аннотации к полям:

```java
import by.pranovich.validationframework.annotation.Email;
import by.pranovich.validationframework.annotation.NotBlank;
import by.pranovich.validationframework.annotation.NotEmpty;
import by.pranovich.validationframework.annotation.Pattern;
import by.pranovich.validationframework.annotation.Range;
import by.pranovich.validationframework.annotation.Size;

import java.util.List;

public class User {
    @NotBlank(message = "name must not be blank")
    @Size(min = 2, max = 30, message = "name length must be between 2 and 30")
    private final String name;

    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be valid")
    private final String email;

    @Range(min = 18, max = 120, message = "age must be between 18 and 120")
    private final int age;

    @NotEmpty(message = "roles must not be empty")
    private final List<String> roles;

    @Pattern(regex = "^[A-Z]{2}-\\d{4}$", message = "code has invalid format")
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

Запустите валидацию через публичный API:

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
            System.out.println(issue.getFieldName() + ": " + issue.getMessage());
        }

        boolean valid = validator.isValid(user);
        System.out.println("Valid: " + valid);
    }
}
```

## Основной API

### `Validation.defaultValidator()`

Создаёт стандартный валидатор со всеми встроенными обработчиками:

```java
Validator validator = Validation.defaultValidator();
```

### `Validator`

Главный интерфейс для пользователя:

```java
List<ValidationIssue> validate(Object target);
boolean isValid(Object target);
```

`validate()` возвращает список найденных ошибок. Если список пустой, объект считается валидным. `isValid()` является удобной короткой проверкой поверх `validate()`.

### `ValidationIssue`

Описывает одну ошибку валидации:

```java
String fieldName = issue.getFieldName();
String message = issue.getMessage();
```

`fieldName` содержит имя поля, на котором найдена ошибка. `message` содержит сообщение из аннотации или служебное сообщение фреймворка.

`toString()` возвращает строку в формате:

```text
field: name. error: field must not be null
```

## Поддерживаемые Аннотации

Все аннотации применяются к полям и доступны во время выполнения программы.

| Аннотация | Поддерживаемый тип | Описание |
| --- | --- | --- |
| `@NotNull` | любой тип | Значение поля не должно быть `null`. |
| `@NotBlank` | `CharSequence` | Значение не должно быть `null`, пустым или состоять только из пробельных символов. |
| `@NotEmpty` | `CharSequence`, массив, `Collection`, `Map` | Значение не должно быть `null` или пустым. |
| `@Size` | `CharSequence`, массив, `Collection`, `Map` | Размер должен быть в диапазоне от `min` до `max` включительно. |
| `@Pattern` | `CharSequence` | Значение должно соответствовать регулярному выражению. |
| `@Email` | `String` | Значение должно быть строкой с email-адресом допустимого формата. |
| `@Range` | `Number` | Число должно быть в диапазоне от `min` до `max` включительно. |
| `@Min` | `Number` | Число должно быть больше или равно указанному минимуму. |
| `@Max` | `Number` | Число должно быть меньше или равно указанному максимуму. |
| `@Positive` | `Number` | Число должно быть строго больше нуля. |
| `@Negative` | `Number` | Число должно быть строго меньше нуля. |

## Параметры Аннотаций

### `@NotNull`

```java
@NotNull(message = "field must not be null")
```

Параметры:

| Параметр | По умолчанию | Описание |
| --- | --- | --- |
| `message` | `"field must not be null"` | Сообщение, если значение равно `null`. |

### `@NotBlank`

```java
@NotBlank(message = "field must not be blank")
```

Параметры:

| Параметр | По умолчанию | Описание |
| --- | --- | --- |
| `message` | `"field must not be blank"` | Сообщение, если значение равно `null`, пустое или состоит только из пробельных символов. |

### `@NotEmpty`

```java
@NotEmpty(message = "field must not be empty")
```

Параметры:

| Параметр | По умолчанию | Описание |
| --- | --- | --- |
| `message` | `"field must not be empty"` | Сообщение, если значение равно `null` или имеет размер `0`. |

### `@Size`

```java
@Size(min = 2, max = 10, message = "size is out of range")
```

Параметры:

| Параметр | По умолчанию | Описание |
| --- | --- | --- |
| `min` | `0` | Минимальный допустимый размер. |
| `max` | `Integer.MAX_VALUE` | Максимальный допустимый размер. |
| `message` | `"size is out of range"` | Сообщение, если размер вне диапазона. |

Если `min` или `max` отрицательные, либо `min > max`, фреймворк вернёт служебную ошибку конфигурации аннотации.

### `@Pattern`

```java
@Pattern(regex = "^[A-Z]{3}$", message = "must match regex")
```

Параметры:

| Параметр | По умолчанию | Описание |
| --- | --- | --- |
| `regex` | нет | Регулярное выражение для проверки. |
| `message` | `"must match regex"` | Сообщение, если значение не соответствует regex. |

### `@Email`

```java
@Email(message = "must be a valid email")
```

Параметры:

| Параметр | По умолчанию | Описание |
| --- | --- | --- |
| `message` | `"must be a valid email"` | Сообщение, если email некорректен. |

### `@Range`

```java
@Range(min = 18, max = 120, message = "must be within range")
```

Параметры:

| Параметр | По умолчанию | Описание |
| --- | --- | --- |
| `min` | нет | Минимальное допустимое значение. |
| `max` | нет | Максимальное допустимое значение. |
| `message` | `"must be within range"` | Сообщение, если число меньше `min` или больше `max`. |

Если `min > max`, фреймворк вернёт служебную ошибку конфигурации аннотации.

### `@Min`

```java
@Min(value = 18, message = "must be greater than or equal to minimum")
```

Параметры:

| Параметр | По умолчанию | Описание |
| --- | --- | --- |
| `value` | нет | Минимальное допустимое значение. |
| `message` | `"must be greater than or equal to minimum"` | Сообщение, если число меньше минимума. |

### `@Max`

```java
@Max(value = 100, message = "must be less than or equal to maximum")
```

Параметры:

| Параметр | По умолчанию | Описание |
| --- | --- | --- |
| `value` | нет | Максимальное допустимое значение. |
| `message` | `"must be less than or equal to maximum"` | Сообщение, если число больше максимума. |

### `@Positive`

```java
@Positive(message = "must be positive")
```

Параметры:

| Параметр | По умолчанию | Описание |
| --- | --- | --- |
| `message` | `"must be positive"` | Сообщение, если число меньше или равно нулю. |

### `@Negative`

```java
@Negative(message = "must be negative")
```

Параметры:

| Параметр | По умолчанию | Описание |
| --- | --- | --- |
| `message` | `"must be negative"` | Сообщение, если число больше или равно нулю. |

## Поведение `null`

`null` как ошибку проверяют `@NotNull`, `@NotBlank` и `@NotEmpty`.

Остальные аннотации пропускают `null`, чтобы их можно было комбинировать с проверкой обязательности:

```java
@NotBlank
@Size(min = 2, max = 30)
private String name;
```

Если `name == null`, будет ошибка от `@NotBlank`. Проверка `@Size` не добавит дополнительную ошибку.

Если в `validator.validate(null)` передать сам объект как `null`, метод вернёт одну ошибку:

```text
fieldName = "object"
message = "validated object is null"
```

## Внутренняя Структура

Основные пакеты:

```text
by.pranovich.validationframework
|-- annotation   # Аннотации валидации
|-- core         # ValidationIssue и ValidationContext
|-- exception    # ValidationException
|-- factory      # Сборка стандартной цепочки обработчиков
|-- handler      # Обработчики аннотаций
|-- util         # Вспомогательные классы
`-- validator    # Интерфейс Validator и ObjectFieldValidator
```

Основные классы:

| Класс | Назначение |
| --- | --- |
| `Validation` | Публичная точка входа, создаёт стандартный валидатор. |
| `Validator` | Интерфейс для запуска валидации. |
| `ObjectFieldValidator` | Обходит поля объекта и запускает цепочку обработчиков. |
| `ValidationHandler` | Базовый класс обработчиков в цепочке. |
| `ValidationHandlerChainFactory` | Создаёт стандартную цепочку обработчиков. |
| `ValidationIssue` | Описывает одну найденную ошибку. |
| `ValidationContext` | Передаёт обработчикам поле, объект и общий список ошибок. |
| `ValueSizeResolver` | Определяет размер строк, массивов, коллекций и map для обработчиков `@Size` и `@NotEmpty`. |
| `ValidationException` | Runtime-исключение для внутренних ошибок фреймворка. |

## Как Работает Цепочка

1. `Validation.defaultValidator()` создаёт `ObjectFieldValidator`.
2. `ObjectFieldValidator` получает поля объекта через reflection.
3. Для каждого поля создаётся `ValidationContext` и запускается цепочка `ValidationHandler`.
4. Каждый обработчик проверяет только свою аннотацию.
5. Найденные ошибки добавляются в общий `List<ValidationIssue>` через `context.addIssue(...)`.

Стандартная цепочка включает обработчики:

```text
NotNull -> NotBlank -> NotEmpty -> Pattern -> Email -> Size -> Range -> Max -> Min -> Positive -> Negative
```

## Создание Валидатора Вручную

Обычно достаточно использовать `Validation.defaultValidator()`. Если нужно создать валидатор явно:

```java
import by.pranovich.validationframework.factory.ValidationHandlerChainFactory;
import by.pranovich.validationframework.handler.ValidationHandler;
import by.pranovich.validationframework.validator.ObjectFieldValidator;
import by.pranovich.validationframework.validator.Validator;

ValidationHandler chain = ValidationHandlerChainFactory.createDefaultChain();
Validator validator = new ObjectFieldValidator(chain);
```

## Добавление Новой Аннотации

Чтобы добавить новое правило:

1. Создайте аннотацию в пакете `by.pranovich.validationframework.annotation`.
2. Добавьте `@Retention(RetentionPolicy.RUNTIME)` и `@Target(ElementType.FIELD)`.
3. Создайте обработчик в пакете `by.pranovich.validationframework.handler`.
4. Унаследуйте обработчик от `ValidationHandler`.
5. В `validate(ValidationContext context)` проверьте наличие своей аннотации через `context.hasAnnotation(...)`.
6. Получите значение поля через `context.getValue()` и добавьте ошибку через `context.addIssue(message)`.
7. Подключите новый обработчик в `ValidationHandlerChainFactory`.

Минимальный пример обработчика:

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

## Ограничения Текущей Реализации

- Валидируются только поля текущего класса: `target.getClass().getDeclaredFields()`.
- Поля родительских классов пока не обходятся.
- `static` поля пока не пропускаются автоматически.
- Числовые обработчики сравнивают значения через `doubleValue()`, поэтому для `BigDecimal`, `BigInteger`, `NaN` и бесконечностей возможны спорные случаи.
- `@Email` использует упрощённый regex и не покрывает весь стандарт email-адресов.
- Валидация методов, параметров методов, классовых ограничений и вложенных объектов пока не реализована.

## Команды Разработки

Скомпилировать проект без запуска тестов:

```bash
mvn clean compile -DskipTests
```

Запустить все тесты:

```bash
mvn clean test
```

Запустить тест одного обработчика:

```bash
mvn -Dtest=SizeHandlerTest test
```
