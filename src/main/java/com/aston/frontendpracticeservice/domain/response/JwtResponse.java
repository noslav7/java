package com.aston.frontendpracticeservice.domain.response;

import lombok.Builder;

public record JwtResponse(String accessToken, String refreshToken) {

    @Builder
    public JwtResponse {
    }
}
