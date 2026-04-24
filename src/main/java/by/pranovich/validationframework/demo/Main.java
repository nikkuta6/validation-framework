package by.pranovich.validationframework.demo;

import by.pranovich.validationframework.core.ValidationIssue;
import by.pranovich.validationframework.factory.ValidationHandlerChainFactory;
import by.pranovich.validationframework.handler.ValidationHandler;
import by.pranovich.validationframework.model.User;
import by.pranovich.validationframework.validator.ObjectFieldValidator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user = new User(23, "Nikita", "nik123@gmail.com",
                new String[] { "football", "programming", "music", "traveling", "cooking" });

        ValidationHandler handler = ValidationHandlerChainFactory.createDefaultChain();
        ObjectFieldValidator validator = new ObjectFieldValidator(handler);

        List<ValidationIssue> issues = validator.validate(user);

        if (issues.isEmpty()) {
            System.out.println("User data is correct!");
        } else {
            issues.forEach(System.out::println);
        }
    }
}
