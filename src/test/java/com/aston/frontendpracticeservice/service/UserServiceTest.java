package com.aston.frontendpracticeservice.service;

import com.aston.frontendpracticeservice.domain.entity.User;
import com.aston.frontendpracticeservice.exception.PasswordForbiddenException;
import com.aston.frontendpracticeservice.exception.UserNotFoundException;
import com.aston.frontendpracticeservice.security.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    private final static UUID ID = UUID.randomUUID();
    private final static String FIRST_NAME = "Test first name";
    private final static String LAST_NAME = "Test last name";
    private final static String DATE_BIRTH = "01.01.2000";
    private final static String INN = "123456789000";
    private final static String SNILS = "123-456-789 00";
    private final static String NUMBER_PASSPORT = "123456";
    private final static String LOGIN = "test@test.ru";
    private final static String LOGIN_SAVE = "test_save@test.ru";
    private final static String LOGIN_NEGATIVE = "test_negative@test.ru";
    private final static String PASSWORD_POSITIVE = "TeSt2000%&";
    private final static String PASSWORD_NEGATIVE = "123456789";
    private final static Role ROLE = Role.USER;
    @Autowired
    private UserService userService;
    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:15.1-alpine")
    );

    @DynamicPropertySource
    private static void dynamicPropertySource(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", POSTGRES::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", POSTGRES::getPassword);
    }
    private User userPositive;
    private User userNegative;
    private User savedUser;

    @BeforeEach
    public void init() {
        userPositive = User.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .dateBirth(DATE_BIRTH)
                .inn(INN)
                .snils(SNILS)
                .numberPassport(NUMBER_PASSPORT)
                .login(LOGIN)
                .password(PASSWORD_POSITIVE)
                .role(ROLE).build();
        userNegative = User.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .dateBirth(DATE_BIRTH)
                .inn(INN)
                .snils(SNILS)
                .numberPassport(NUMBER_PASSPORT)
                .login(LOGIN)
                .password(PASSWORD_NEGATIVE)
                .role(ROLE).build();
        savedUser = User.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .dateBirth(DATE_BIRTH)
                .inn(INN)
                .snils(SNILS)
                .numberPassport(NUMBER_PASSPORT)
                .login(LOGIN_SAVE)
                .password(PASSWORD_POSITIVE)
                .role(ROLE).build();
    }

    @Test
    @DisplayName("Проверка успешного сохранения пользователя в базе данных")
    public void save_shouldReturnResponse() {
        User response = userService.save(savedUser);

        Assertions.assertThat(response.getFirstName()).isEqualTo(savedUser.getFirstName());
        Assertions.assertThat(response.getLastName()).isEqualTo(savedUser.getLastName());
        Assertions.assertThat(response.getDateBirth()).isEqualTo(savedUser.getDateBirth());
        Assertions.assertThat(response.getInn()).isEqualTo(savedUser.getInn());
        Assertions.assertThat(response.getSnils()).isEqualTo(savedUser.getSnils());
        Assertions.assertThat(response.getNumberPassport()).isEqualTo(savedUser.getNumberPassport());
        Assertions.assertThat(response.getLogin()).isEqualTo(savedUser.getLogin());
        Assertions.assertThat(response.getPassword()).isEqualTo(savedUser.getPassword());
        Assertions.assertThat(response.getRole()).isEqualTo(savedUser.getRole());
    }

    @Test
    @DisplayName("Проверка негативного сценария сохранения пользователя в базе данных")
    public void save_shouldReturnPasswordError() {
        Assertions.assertThatExceptionOfType(PasswordForbiddenException.class).isThrownBy(() ->
                userService.save(userNegative));
    }

    @Test
    @DisplayName("Проверка успешного поиска пользователя по логину в базе данных")
    public void findByLogin_shouldReturnResponse() {
        User saveUser = userService.save(userPositive);
        User response = userService.findByLogin(saveUser.getLogin());

        Assertions.assertThat(response.getFirstName()).isEqualTo(saveUser.getFirstName());
        Assertions.assertThat(response.getLastName()).isEqualTo(saveUser.getLastName());
        Assertions.assertThat(response.getDateBirth()).isEqualTo(saveUser.getDateBirth());
        Assertions.assertThat(response.getInn()).isEqualTo(saveUser.getInn());
        Assertions.assertThat(response.getSnils()).isEqualTo(saveUser.getSnils());
        Assertions.assertThat(response.getNumberPassport()).isEqualTo(saveUser.getNumberPassport());
        Assertions.assertThat(response.getLogin()).isEqualTo(saveUser.getLogin());
        Assertions.assertThat(response.getPassword()).isEqualTo(saveUser.getPassword());
        Assertions.assertThat(response.getRole()).isEqualTo(saveUser.getRole());
    }

    @Test
    @DisplayName("Проверка негативного сценария поиска пользователя по логину в базе данных")
    public void findByLogin_shouldReturnUserError() {
        Assertions.assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() ->
                userService.findByLogin(LOGIN_NEGATIVE));
    }
}
