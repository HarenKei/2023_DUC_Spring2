package com.example.ch09.controller;

import com.example.ch09.dto.CreateAccessTokenRequest;
import com.example.ch09.dto.CreateAccessTokenResponse;
import com.example.ch09.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
            @RequestBody CreateAccessTokenRequest request
            ) {
        String newToken = tokenService.createNewAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(new CreateAccessTokenResponse(newToken));
    }
}
