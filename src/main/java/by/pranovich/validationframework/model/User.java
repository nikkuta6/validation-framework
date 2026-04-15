package by.pranovich.validationframework.model;

import by.pranovich.validationframework.annotations.Email;
import by.pranovich.validationframework.annotations.NotNull;
import by.pranovich.validationframework.annotations.Size;

public class User {
    @NotNull(message = "Name can't be null!")
    @Size(min = 3, max = 20, message = "Field size should be between 3 and 20!")
    public String name;

    @NotNull(message = "Email can't be null!")
    @Email(message = "Email is incorrect!")
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append(", email: ").append(email);
        return sb.toString();
    }
}
