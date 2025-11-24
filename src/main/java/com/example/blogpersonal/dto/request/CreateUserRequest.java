package com.example.blogpersonal.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {

    @NotBlank(message = "El username es obligatorio")
    @Pattern(regexp = "...")
    @Pattern(regexp = "^[A-Za-z0-9._-]+$", message = "El username solo puede contener letras, números, '.', '_' o '-'")
    private String username;
    @NotBlank(message = "Password obligatorio")
    @Size(min = 8, max = 70, message = "El password debe tener entre 8 y 70 caracteres")
    private String password;
    @Email(message = "Debe ser un email valido")
    @NotBlank(message = "Email obligatorio")
    private String email;
    @NotBlank(message = "El primer nombre es obligatorio")
    private String firstName;
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;
    //Descripcion es opcional
    private String description;


    public CreateUserRequest() {
    }

    public CreateUserRequest(String username,String password, String email, String firstName, String lastName, String description) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
