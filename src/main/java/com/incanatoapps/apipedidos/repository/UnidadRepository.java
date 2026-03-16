package com.incanatoapps.apipedidos.repository;

import com.incanatoapps.apipedidos.entity.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadRepository extends JpaRepository<Unidad, String> {

}
