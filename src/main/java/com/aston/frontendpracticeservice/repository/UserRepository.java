package com.aston.frontendpracticeservice.repository;

import com.aston.frontendpracticeservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


/**
 * Интерфейс-репозиторий для работы с методами профиля пользователя.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры:
 * {@link User} - класс-сущность
 * {@link UUID} - идентификатор
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByLogin(String login);
}
