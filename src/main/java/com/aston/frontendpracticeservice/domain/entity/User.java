package com.aston.frontendpracticeservice.domain.entity;

import com.aston.frontendpracticeservice.security.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

import static com.aston.frontendpracticeservice.constant.RegexTextMessageConstant.EMAIL_REGEX;

@Entity
@Table(name = "user_profile")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
@ToString
@EqualsAndHashCode
@Schema(description = "Объект профиля пользователя")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    @Schema(description = "Уникальный идентификатор пользователя")
    private UUID id;
    @NotEmpty(message = "Поле имени не должно быть пустым!")
    @Size(min = 2, max = 64, message = "Имя должно содержать от 2 до 64 символов!")
    @Schema(description = "Имя пользователя")
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message = "Поле фамилии не должно быть пустым!")
    @Size(min = 2, max = 64, message = "Фамилия должна содержать от 2 до 64 символов!")
    @Schema(description = "Фамилия пользователя")
    @Column(name = "last_name")
    private String lastName;
    @NotEmpty(message = "Поле даты рождения не должно быть пустым!")
    @Size(min = 10, max = 10, message = "Дата рождения должна содержать 10 символов!")
    @Schema(description = "Дата рождения пользователя")
    @Column(name = "date_birth")
    private String dateBirth;
    @NotEmpty(message = "Поле ИНН пользователя не должно быть пустым!")
    @Size(min = 12, max = 12, message = "ИНН пользователя должен содержать 12 символов!")
    @Schema(description = "ИНН пользователя")
    @Column(name = "inn")
    private String inn;
    @NotEmpty(message = "Поле СНИЛС пользователя не должно быть пустым!")
    @Size(min = 14, max = 14, message = "СНИЛС пользователя должен содержать 14 символов!")
    @Schema(description = "СНИЛС пользователя")
    @Column(name = "snils")
    private String snils;
    @NotEmpty(message = "Поле номера паспорта не должно быть пустым!")
    @Size(min = 6, max = 6, message = "Номер паспорта пользователя должен содержать 6 символов!")
    @Schema(description = "Номер паспорта пользователя")
    @Column(name = "number_passport")
    private String numberPassport;
    @NotEmpty(message = "Поле логина (электронная почта) не должно быть пустым!")
    @Size(min = 6, max = 64, message = "Логин (электронная почта) должен содержать от 6 до 64 символов!")
    @Email(message = "Некорректный логин", regexp = EMAIL_REGEX)
    @Schema(description = "Логин пользователя")
    @Column(name = "login")
    private String login;
    @NotEmpty(message = "Поле пароля не должно быть пустым!")
    @Size(min = 6, max = 20, message = "Пароль должен содержать не менее 6 и не более 20 знаков, строчные и заглавные буквы латинского алфавита, цифры и специальные символы")
    @Schema(description = "Пароль пользователя")
    @Column(name = "password")
    private String password;
    @Schema(description = "Роль пользователя")
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}
