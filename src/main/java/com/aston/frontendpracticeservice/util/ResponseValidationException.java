package com.aston.frontendpracticeservice.util;

import java.util.List;

/**
 * Запись, для построения исключений связанных с валидацией, с полной информацией о них
 *
 * @param violations список исключений
 */
public record ResponseValidationException(List<Violation> violations) {
}
