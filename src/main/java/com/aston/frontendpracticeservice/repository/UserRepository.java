package com.aston.frontendpracticeservice.repository;

import com.aston.frontendpracticeservice.domain.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Mock repository
 */
@Component
public class UserRepository {

    @Value("${auth.default_login}")
    private String defaultLogin;

    @Value("${auth.default_password}")
    private String defaultPassword;

    private List<User> users = new ArrayList<>();

    @PostConstruct
    public void initDefaultUser() {
        users.add(User.builder()
                .login(defaultLogin)
                .password(defaultPassword)
                .build());
    }

    public Optional<User> findByLogin(String login) {
        return users.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst();
    }
}
