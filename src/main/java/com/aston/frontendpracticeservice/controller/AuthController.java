package com.aston.frontendpracticeservice.controller;

import com.aston.frontendpracticeservice.domain.request.AuthRequest;
import com.aston.frontendpracticeservice.domain.request.RefreshJwtRequest;
import com.aston.frontendpracticeservice.domain.response.JwtResponse;
import com.aston.frontendpracticeservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontend-practice/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authAndGetToken(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authAndGetToken(authRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
