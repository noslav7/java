package com.aston.frontendpracticeservice.handler;

import com.aston.frontendpracticeservice.domain.response.SimpleMessage;
import com.aston.frontendpracticeservice.exception.AuthException;
import com.aston.frontendpracticeservice.exception.PasswordForbiddenException;
import com.aston.frontendpracticeservice.exception.UserNotFoundException;
import com.aston.frontendpracticeservice.util.ResponseValidationException;
import com.aston.frontendpracticeservice.util.Violation;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected SimpleMessage handleMethodArgumentNotValid(IllegalArgumentException exception) {
        return new SimpleMessage(exception.getMessage());
    }

    @ExceptionHandler(InvalidMediaTypeException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    protected SimpleMessage handleMethodArgumentNotValid(InvalidMediaTypeException exception) {
        return new SimpleMessage(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected SimpleMessage handleUserNotFoundException(UserNotFoundException exception) {
        return new SimpleMessage(exception.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected SimpleMessage handleAuthException(AuthException exception) {
        return new SimpleMessage(exception.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected SimpleMessage handleJwtException(JwtException exception) {
        return new SimpleMessage(exception.getMessage());
    }

    @ExceptionHandler(PasswordForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected SimpleMessage passwordForbiddenException(PasswordForbiddenException exception) {
        return new SimpleMessage(exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ResponseValidationException> onConstraintValidationException(ConstraintViolationException exception) {
        final List<Violation> violations = exception.getConstraintViolations().stream()
                .map(violation ->
                        new Violation(violation.getPropertyPath().toString(), violation.getMessage(), getDateTime())
                ).collect(Collectors.toList());

        return ResponseEntity.ok(new ResponseValidationException(violations));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseValidationException> onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final List<Violation> violations = exception.getBindingResult().getFieldErrors().stream()
                .map(violation ->
                        new Violation(violation.getField(), violation.getDefaultMessage(), getDateTime())
                ).collect(Collectors.toList());

        return ResponseEntity.ok(new ResponseValidationException(violations));
    }

    private LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }
}
