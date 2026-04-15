package by.pranovich.validationframework.demo;

import by.pranovich.validationframework.core.ValidationError;
import by.pranovich.validationframework.handlers.EmailHandler;
import by.pranovich.validationframework.handlers.NotNullHandler;
import by.pranovich.validationframework.handlers.SizeHandler;
import by.pranovich.validationframework.handlers.ValidationHandler;
import by.pranovich.validationframework.model.User;
import by.pranovich.validationframework.validator.Validator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user = new User("Ni", null);

        ValidationHandler notNullHandler = new NotNullHandler();
        ValidationHandler emailHandler = new EmailHandler();
        ValidationHandler sizeHandler = new SizeHandler();

        notNullHandler.linkWith(emailHandler).linkWith(sizeHandler);

        Validator validator = new Validator(notNullHandler);

        List<ValidationError> errors = validator.validate(user);

        if (errors.isEmpty()) {
            System.out.println("User is valid");
        } else {
            errors.forEach(System.out::println);
        }
    }
}
