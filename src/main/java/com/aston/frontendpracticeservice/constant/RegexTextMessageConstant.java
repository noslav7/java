package com.aston.frontendpracticeservice.constant;

/**
 * Этот класс содержит текстовые константные переменные для всех регулярных выражений
 */
public class RegexTextMessageConstant {
    public final static String EMAIL_REGEX = "^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,20}$";
}
