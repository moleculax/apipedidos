package com.incanatoapps.apipedidos.validator;

import com.incanatoapps.apipedidos.dto.ClienteDTO;
import com.incanatoapps.apipedidos.exception.ValidateException;

public class ClienteValidator {
    public static void save(ClienteDTO registro){
        if(registro.getNombre()==null || registro.getNombre().trim().isEmpty()){
            throw new ValidateException("El nombre del cliente es requerido");
        }
        if(registro.getTipoDocumento()==null || registro.getTipoDocumento().trim().isEmpty()){
            throw new ValidateException("El tipo de documento del cliente es requerido");
        }
        if(registro.getNumeroDocumento()==null || registro.getNumeroDocumento().trim().isEmpty()){
            throw new ValidateException("El numero de documento del cliente es requerido");
        }


    }

}// END CLASS
