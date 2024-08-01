package com.aston.frontendpracticeservice.service;

import com.aston.frontendpracticeservice.domain.entity.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
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
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private UserService userService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        userService.findAll().forEach(user -> userService.deleteById(user.getId())); // clear database before each test
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("First name must not be blank", violations.iterator().next().getMessage());
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
                .build();

        userService.save(user1);
        userService.save(user2);

        assertEquals(2, userService.findAll().size());
    }

    @Test
    void testFindById() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .inn("123456789012")
                .snils("12345678901")
                .passportNumber("1234567890")
                .login("user1")
                .password("password1")
                .build();

        userService.save(user);
        Optional<User> foundUser = userService.findById(user.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(user.getLogin(), foundUser.get().getLogin());
    }

    @Test
    void testFindByLogin() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .inn("123456789012")
                .snils("12345678901")
                .passportNumber("1234567890")
                .login("user1")
                .password("password1")
                .build();

        userService.save(user);
        Optional<User> foundUser = userService.findByLogin(user.getLogin());

        assertTrue(foundUser.isPresent());
        assertEquals(user.getFirstName(), foundUser.get().getFirstName());
    }

    @Test
    void testDeleteById() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .inn("123456789012")
                .snils("12345678901")
                .passportNumber("1234567890")
                .login("user1")
                .password("password1")
                .build();

        userService.save(user);
        userService.deleteById(user.getId());

        Optional<User> foundUser = userService.findById(user.getId());
        assertTrue(foundUser.isEmpty());
    }

    @Test
    void testExistsById() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .inn("123456789012")
                .snils("12345678901")
                .passportNumber("1234567890")
                .login("user1")
                .password("password1")
                .build();

        userService.save(user);
        assertTrue(userService.existsById(user.getId()));
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
