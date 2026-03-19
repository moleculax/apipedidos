package com.incanatoapps.apipedidos.validator;

import com.incanatoapps.apipedidos.dto.UsuarioDTO;
import com.incanatoapps.apipedidos.entity.Usuario;
import com.incanatoapps.apipedidos.exception.ValidateException;

public class UsuarioValidator {

    public static void save(UsuarioDTO registro) {
        if (registro.getEmail() == null || registro.getEmail().isEmpty()) {
            throw new ValidateException("El email no puede ser nulo y es requerido");
        }
        if (registro.getPassword() == null || registro.getPassword().isEmpty()) {
            throw new ValidateException("El password no puede ser nulo es requerido");
        }
        if (registro.getRoles()  == null ) {
            throw new ValidateException("El roles no puede ser nulo es requerido");
        }
    }


}// END CLASS VALIDATOR
