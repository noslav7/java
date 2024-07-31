package com.aston.frontendpracticeservice.service;

import com.aston.frontendpracticeservice.domain.entity.User;
import com.aston.frontendpracticeservice.repository.UserRepositoryJPA;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {UserServiceTest.Initializer.class})
public class UserServiceTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("puppet_db")
            .withUsername("postgres")
            .withPassword("postgres");

    @Autowired
    private UserRepositoryJPA userRepositoryJPA;

    @Autowired
    private UserService userService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        userRepositoryJPA.deleteAll();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUserCreation() {
        User user = new User();
                user.setFirstName("John");
                user.setLastName("Doe");
                user.setDateOfBirth(LocalDate.of(1990, 1, 1));
                user.setInn("123456789012");
                user.setSnils("12345678901");
                user.setPassportNumber("1234567890");
                user.setLogin("validuser");
                user.setPassword("validpassword");
                user.setRoles(Set.of());

        System.out.println("User before saving: " + user);
        userRepositoryJPA.save(user);
        User foundUser = userService.findByLogin("validuser");
        assertEquals("validuser", foundUser.getLogin());
    }

    @Test
    void testInvalidInnTooShort() {
        User user = User.builder()
                .login("invaliduser")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .inn("123")
                .snils("12345678901")
                .passportNumber("1234567890")
                .roles(Set.of())
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("size must be between 12 and 12", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidSnilsTooLong() {
        User user = User.builder()
                .login("invaliduser")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .inn("123456789012")
                .snils("123456789012345")
                .passportNumber("1234567890")
                .roles(Set.of())
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("size must be between 11 and 11", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidDateOfBirthInFuture() {
        User user = User.builder()
                .login("futureuser")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(2100, 1, 1))
                .inn("123456789012")
                .snils("12345678901")
                .passportNumber("1234567890")
                .roles(Set.of())
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("must be a past date", violations.iterator().next().getMessage());
    }

    @Test
    void testMissingFirstName() {
        User user = User.builder()
                .login("userwithoutfirstname")
                .password("password")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .inn("123456789012")
                .snils("12345678901")
                .passportNumber("1234567890")
                .roles(Set.of())
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testMissingPassword() {
        User user = User.builder()
                .login("userwithoutpassword")
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .inn("123456789012")
                .snils("12345678901")
                .passportNumber("1234567890")
                .roles(Set.of())
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testFindAllUsers() {
        User user1 = User.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .inn("123456789012")
                .snils("12345678901")
                .passportNumber("1234567890")
                .login("user1")
                .password("password1")
                .roles(Set.of())
                .build();

        User user2 = User.builder()
                .login("user2")
                .password("password2")
                .firstName("Jane")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(1992, 2, 2))
                .inn("234567890123")
                .snils("23456789012")
                .passportNumber("2345678901")
                .roles(Set.of())
                .build();

        System.out.println("User before saving: " + user1);
        System.out.println("User before saving: " + user2);
        userRepositoryJPA.save(user1);
        userRepositoryJPA.save(user2);

        assertEquals(2, userService.findAll().size());
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
