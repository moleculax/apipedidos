package com.menuadmin.apipedidos.validator;

import com.menuadmin.apipedidos.dto.ClienteDTO;
import com.menuadmin.apipedidos.exception.ValidateException;

public class ClienteValidator {
    public static void save(ClienteDTO registro) {
        if (registro.getNombre() == null || registro.getNombre().trim().isEmpty()) {
            throw new ValidateException("El nombre del cliente es requerido");
        }
        if (registro.getNumeroDocumento() == null || registro.getNumeroDocumento().trim().isEmpty()) {
            throw new ValidateException("El número de documento es requerido");
        }
        if (registro.getTipoDocumento() == null) {
            throw new ValidateException("El tipo de documento es requerido");
        }
    }
}
