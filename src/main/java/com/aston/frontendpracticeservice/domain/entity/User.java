package com.aston.frontendpracticeservice.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @Past
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotBlank
    @Size(min = 12, max = 12)
    private String inn;

    @NotBlank
    @Size(min = 11, max = 11)
    private String snils;

    @Size(min = 10, max = 10)
    @Column(name = "passport_number", nullable = false)
    private String passportNumber;

    @NotBlank
    private String login;

    @NotBlank
    @Size(min = 8)
    private String password;
}
