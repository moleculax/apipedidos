package com.menuadmin.apipedidos.repository;

import com.menuadmin.apipedidos.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Page<Usuario> findByEmailContainingIgnoreCase(Pageable pageable, String email);
    Optional<Usuario> findByEmail(String email);
}
