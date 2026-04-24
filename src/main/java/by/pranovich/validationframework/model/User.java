package by.pranovich.validationframework.model;

import java.util.Arrays;

import by.pranovich.validationframework.annotation.Email;
import by.pranovich.validationframework.annotation.Max;
import by.pranovich.validationframework.annotation.Min;
import by.pranovich.validationframework.annotation.NotNull;
import by.pranovich.validationframework.annotation.Pattern;
import by.pranovich.validationframework.annotation.Size;

public class User {
    @Max(value = 100, message = "Id should be less than 100!")
    @Min(value = 1, message = "Id should be greater than 0!")
    private long id;

    @NotNull(message = "Name can't be null!")
    @Size(min = 3, max = 20, message = "Field size should be between 3 and 20!")
    @Pattern(regex = "^[a-zA-Z]+$", message = "Name should contain only letters: ")
    private String name;

    @NotNull(message = "Email can't be null!")
    @Email(message = "Email is incorrect!")
    private String email;

    @Size(min = 1, max = 5, message = "Hobbies count should be between 1 and 5!")
    private String hobbies[];

    public User(long id, String name, String email, String[] hobbies) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.hobbies = hobbies.clone();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String[] getHobbies() {
        return hobbies.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(id).append(", name: ").append(name).append(", email: ").append(email)
                .append(", hobbies: ").append(Arrays.toString(hobbies));
        return sb.toString();
    }
}
