# Validation Framework

Учебный Java-фреймворк для валидации объектов на основе собственных аннотаций и паттерна
**Chain of Responsibility**.

## Возможности

- Валидация полей объекта через reflection.
- Собственные аннотации:
  - `@NotNull`
  - `@Email`
  - `@Min`
  - `@Max`
  - `@Size`
  - `@Pattern`
  - `@Positive`
  - `@Negative`
- Отдельный обработчик для каждой аннотации.
- Сборка цепочки обработчиков через `ValidationHandlerChainFactory`.
- Возврат списка ошибок в виде объектов `ValidationIssue`.
- Демонстрационный пример в `by.pranovich.validationframework.demo.Main`.

## Структура проекта

```text
by.pranovich.validationframework
├── annotation   # Пользовательские аннотации валидации
├── core         # Общие классы фреймворка
├── demo         # Пример использования
├── factory      # Создание цепочки обработчиков
├── handler      # Обработчики правил валидации
├── model        # Демонстрационные модели
└── validator    # Запуск валидации объекта
```

## Основные классы

- `ObjectFieldValidator` - обходит поля объекта и запускает цепочку обработчиков.
- `ValidationHandler` - базовый класс обработчика в цепочке.
- `ValidationHandlerChainFactory` - создает стандартную цепочку валидаторов.
- `ValidationIssue` - описывает найденную ошибку валидации.
- `User` - демонстрационная модель с аннотациями.

## Как это работает

1. На поля модели добавляются аннотации валидации.
2. `ValidationHandlerChainFactory.createDefaultChain()` создает цепочку обработчиков.
3. `ObjectFieldValidator` получает объект и для каждого поля запускает цепочку.
4. Каждый handler проверяет только свою аннотацию.
5. Все найденные ошибки добавляются в список `ValidationIssue`.

Пример:

```java
User user = new User(
    23,
    "Nikita",
    "nik123@gmail.com",
    new String[] { "football", "programming", "music" }
);

ValidationHandler handler = ValidationHandlerChainFactory.createDefaultChain();
ObjectFieldValidator validator = new ObjectFieldValidator(handler);

List<ValidationIssue> issues = validator.validate(user);
```

## Поддерживаемые аннотации

| Аннотация | Назначение |
| --- | --- |
| `@NotNull` | Значение поля не должно быть `null`. |
| `@Email` | Значение должно быть строкой с корректным email. |
| `@Min` | Числовое значение должно быть не меньше указанного минимума. |
| `@Max` | Числовое значение должно быть не больше указанного максимума. |
| `@Size` | Размер строки, массива, коллекции или map должен быть в заданных границах. |
| `@Pattern` | Строка должна соответствовать регулярному выражению. |
| `@Positive` | Числовое значение должно быть больше нуля. |
| `@Negative` | Числовое значение должно быть меньше нуля. |

## Добавление нового правила

Чтобы добавить новое правило валидации:

1. Создать аннотацию в пакете `annotation`.
2. Создать обработчик в пакете `handler`, унаследовав его от `ValidationHandler`.
3. В обработчике проверить наличие своей аннотации через `field.isAnnotationPresent(...)`.
4. Добавить обработчик в цепочку в `ValidationHandlerChainFactory`.

## Запуск

Сборка и проверка проекта:

```bash
mvn clean test
```

Запуск демонстрационного примера после сборки:

```bash
java -cp target/classes by.pranovich.validationframework.demo.Main
```

## Текущий статус

Все основные обработчики подключены в стандартную цепочку. Следующий полезный шаг для проекта -
добавить unit-тесты для каждого handler-а и для `ObjectFieldValidator`.
