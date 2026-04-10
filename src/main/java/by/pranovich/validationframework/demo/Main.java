package by.pranovich.validationframework.demo;

import by.pranovich.validationframework.core.ValidationError;
import by.pranovich.validationframework.handlers.NotNullHandler;
import by.pranovich.validationframework.handlers.ValidationHandler;
import by.pranovich.validationframework.model.User;
import by.pranovich.validationframework.validator.Validator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user = new User("Nikita", null);
        user.greet();

        ValidationHandler handler = new NotNullHandler();
        Validator validator = new Validator(handler);

        List<ValidationError> errors = validator.validate(user);

        if (errors.isEmpty()) {
            System.out.println("User is valid");
        } else {
            errors.forEach(System.out::println);
        }
    }
}
