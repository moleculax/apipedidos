package com.incanatoapps.apipedidos.service;

import com.incanatoapps.apipedidos.dto.AuthResponseDTO;
import com.incanatoapps.apipedidos.dto.LoginRequestDTO;
import com.incanatoapps.apipedidos.entity.Usuario;
import com.incanatoapps.apipedidos.exception.NoDataFoundException;
import com.incanatoapps.apipedidos.repository.RolRepository;
import com.incanatoapps.apipedidos.repository.UsuarioRepository;
import com.incanatoapps.apipedidos.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoDataFoundException("Usuario no encontrado"));

        String jwtToken = jwtService.generateToken(usuario);

        return AuthResponseDTO.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .token(jwtToken)
                .build();
    }
}
