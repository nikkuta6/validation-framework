package by.pranovich.validationframework.model;

import by.pranovich.validationframework.annotations.NotNull;

public class User {
    @NotNull(message = "Name can't be null!")
    public String name;

    @NotNull(message = "Email can't be null!")
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void greet() {
        System.out.println("Hello, it's User!");
    }
}
