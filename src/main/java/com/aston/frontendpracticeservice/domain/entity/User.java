package com.aston.frontendpracticeservice.domain.entity;

import com.aston.frontendpracticeservice.security.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class User {
    private String login;

    private String password;

    private Set<Role> roles;
}
