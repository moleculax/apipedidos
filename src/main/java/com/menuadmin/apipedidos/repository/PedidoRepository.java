package com.menuadmin.apipedidos.repository;

import com.menuadmin.apipedidos.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Buscar pedidos por rango de fechas
    Page<Pedido> findByFechaBetween(Pageable pageable, LocalDate fechaInicio, LocalDate fechaFin);
    Page<Pedido> findByCorrelativoContainingIgnoreCase(Pageable pageable, String nombre);
}
