package com.aston.frontendpracticeservice.service;

import com.aston.frontendpracticeservice.domain.entity.User;
import com.aston.frontendpracticeservice.exception.PasswordForbiddenException;
import com.aston.frontendpracticeservice.exception.UserNotFoundException;
import com.aston.frontendpracticeservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.aston.frontendpracticeservice.constant.ExceptionTextMessageConstant.PASSWORD_FORBIDDEN_EXCEPTION_MESSAGE;
import static com.aston.frontendpracticeservice.constant.RegexTextMessageConstant.PASSWORD_REGEX;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {
    private final UserRepository userRepository;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    public User findByLogin(@NonNull String login) {
        return userRepository.findUserByLogin(login).orElseThrow(() ->
                new UserNotFoundException("User not found"));
    }

    public User save(@Valid User user) {
        Matcher matcher = PASSWORD_PATTERN.matcher(user.getPassword());
        if (matcher.matches()) {
            return userRepository.save(user);
        }
        throw new PasswordForbiddenException(PASSWORD_FORBIDDEN_EXCEPTION_MESSAGE);
    }
}
