package com.incanatoapps.apipedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 @Data encima de una clase, Lombok genera automáticamente los siguientes métodos:
Getters: Un getCampo() para cada campo.
Setters: Un setCampo() para cada campo (que no sea final).
toString(): Un método que imprime el nombre de la clase y sus valores (súper útil para hacer debug).
equals() y hashCode(): Implementaciones estándar que comparan los objetos por sus valores internos en lugar
de por su dirección de memoria.
 */
@Data
@NoArgsConstructor // generar automáticamente un constructor sin argumentos
@AllArgsConstructor // automáticamente un constructor que incluye un parámetro por cada campo definido en la clase
@Builder

public class ClienteDTO {
    private Long id;
    private String nombre;
    private String tipoDocumento;
    private String numeroDocumento;
    private String direccion;
    private String telefono;
    private String email;

}
