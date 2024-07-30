package com.aston.frontendpracticeservice.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Past
    private LocalDate dateOfBirth;

    @NotBlank
    @Size(min = 12, max = 12)
    private String inn;

    @NotBlank
    @Size(min = 11, max = 11)
    private String snils;

    @NotBlank
    @Size(min = 10, max = 10)
    private String passportNumber;

    @NotBlank
    private String login;

    @NotBlank
    @Size(min = 8)
    private String password;
}
