package com.menuadmin.apipedidos.repository;

import com.menuadmin.apipedidos.entity.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadRepository extends JpaRepository<Unidad, String> {

}
