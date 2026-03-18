package com.incanatoapps.apipedidos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDocumento {

    DNI("DNI",0),
    RUC("RUC",11),
    CE("Carnet de extranjeria",12),
    PASAPORTE("Pasaporte",20);

    private final String descripcion;
    private final int maxLength;

}// END CLASS ENUM
