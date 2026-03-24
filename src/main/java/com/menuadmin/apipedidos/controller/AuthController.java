package com.menuadmin.apipedidos.controller;

import com.menuadmin.apipedidos.dto.ApiResponse;
import com.menuadmin.apipedidos.dto.AuthResponseDTO;
import com.menuadmin.apipedidos.dto.LoginRequestDTO;
import com.menuadmin.apipedidos.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@RequestBody LoginRequestDTO request) {
        return new ApiResponse<>(authService.login(request),true,"success").createResponse(HttpStatus.OK);
    }
}
