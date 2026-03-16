package com.incanatoapps.apipedidos.repository;

import com.incanatoapps.apipedidos.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByCodigo(String codigo);
    Page<Producto> findByNombreContainingIgnoreCase(Pageable pageable, String nombre);
}
