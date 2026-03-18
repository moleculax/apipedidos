package com.incanatoapps.apipedidos.repository;

import com.incanatoapps.apipedidos.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByNumeroDocumento(String numeroDocumento);

    Page<Cliente> findByNumeroDocumentoContainingIgnoreCase(Pageable pageable, String nombre);


}
