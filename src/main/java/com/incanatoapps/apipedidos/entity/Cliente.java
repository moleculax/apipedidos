package com.incanatoapps.apipedidos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Length;
import jakarta.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clientes")

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", length = 20, nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento", length = 15,nullable = false)
    private String numeroDocumento;
    @Column(length = 25, nullable = false)
    private String nombre;
    @Column(length = 100)
    private String direccion;
    @Column(length = 100)
    private String telefono;
    @Column(length = 100)
    private String email;

}
