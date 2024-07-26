package com.aston.frontendpracticeservice.controller;

import com.aston.frontendpracticeservice.domain.entity.User;
import com.aston.frontendpracticeservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontend-practice/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    /**
     * Этот метод позволяет сохранить пользователя в базе данных
     *
     * @param user объект пользователя
     * @return Возвращает ответ пользователю с личной информацией
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый пользователь успешно создан (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Запрос не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для сохранения нового пользователя",
            description = "Позволяет сохранить нового пользователя в приложении")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        User response = userService.save(user);
        return ResponseEntity.ok(response);
    }

    /**
     * Этот метод позволяет найти пользователя по логину в базе данных
     *
     * @param login логин пользователя
     * @return Возвращает найденного пользователю с личной информацией
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый пользователь успешно найден (OK)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Запрос не найден (Not Found)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод для поиска пользователя по логину",
            description = "Позволяет найти пользователя по логину в приложении")
    @GetMapping(value = "/find-login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findByLogin(@Parameter(description = "Логин пользователя")
                                                @RequestParam @NonNull String login) {
        User response = userService.findByLogin(login);
        return ResponseEntity.ok(response);
    }
}
