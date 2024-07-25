package com.aston.frontendpracticeservice.exception;

/**
 * Класс-исключение связанное с неправильно введенным паролем пользователя.
 * Наследуется от класса {@link RuntimeException}
 */
public class PasswordForbiddenException extends RuntimeException {
    public PasswordForbiddenException(String message) {
        super(message);
    }
}
