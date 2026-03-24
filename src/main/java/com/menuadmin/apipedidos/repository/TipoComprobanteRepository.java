package com.menuadmin.apipedidos.repository;

import com.menuadmin.apipedidos.entity.TipoComprobante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoComprobanteRepository extends JpaRepository<TipoComprobante, String> {
    Optional<TipoComprobante> findByCodigo (String codigo);
}
