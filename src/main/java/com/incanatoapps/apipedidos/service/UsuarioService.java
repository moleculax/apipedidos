package com.incanatoapps.apipedidos.service;

import com.incanatoapps.apipedidos.dto.UsuarioDTO;
import com.incanatoapps.apipedidos.entity.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService extends PageableService<UsuarioDTO, Integer> {


}// END CLASS INTERFACE
