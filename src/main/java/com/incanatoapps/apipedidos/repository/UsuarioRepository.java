package com.incanatoapps.apipedidos.repository;

import com.incanatoapps.apipedidos.entity.Rol;
import com.incanatoapps.apipedidos.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    /* Pageable: paginación en Spring Boot (Spring Data JPA),
        necesitas pasar un objeto Pageable a tu
         método del repositorio y cambiar el tipo de retorno a Page<T> o Slice<T>.
     */
    Page<Usuario> findByEmailContainingIgnoreCase(Pageable pageable, String email);
    Optional<Usuario> findByEmail(String email);



}//END INTERFACE
