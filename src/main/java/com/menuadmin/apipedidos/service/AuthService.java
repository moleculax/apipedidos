package com.menuadmin.apipedidos.service;

import com.menuadmin.apipedidos.dto.AuthResponseDTO;
import com.menuadmin.apipedidos.dto.LoginRequestDTO;
import com.menuadmin.apipedidos.entity.Usuario;
import com.menuadmin.apipedidos.exception.NoDataFoundException;
import com.menuadmin.apipedidos.repository.RolRepository;
import com.menuadmin.apipedidos.repository.UsuarioRepository;
import com.menuadmin.apipedidos.security.jwt.JwtService;
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
