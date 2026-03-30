package by.pranovich.validationframework.demo;

import by.pranovich.validationframework.core.ValidationError;
import by.pranovich.validationframework.core.Validator;
import by.pranovich.validationframework.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user = new User(null, "123");

        Validator validator = new Validator();
        List<ValidationError> errors = validator.validate(user);

        if (errors.isEmpty()) {
            System.out.println("Object is valid.");
        } else {
            for (ValidationError error : errors) {
                System.out.println(error);
            }
        }
    }
}
