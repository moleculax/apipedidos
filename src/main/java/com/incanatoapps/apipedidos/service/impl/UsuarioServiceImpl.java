package com.incanatoapps.apipedidos.service.impl;

import com.incanatoapps.apipedidos.dto.UsuarioDTO;
import com.incanatoapps.apipedidos.entity.Cliente;
import com.incanatoapps.apipedidos.entity.Usuario;
import com.incanatoapps.apipedidos.exception.NoDataFoundException;
import com.incanatoapps.apipedidos.exception.ValidateException;
import com.incanatoapps.apipedidos.mapper.UsuarioMapper;
import com.incanatoapps.apipedidos.repository.UsuarioRepository;
import com.incanatoapps.apipedidos.service.UsuarioService;
import com.incanatoapps.apipedidos.validator.UsuarioValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioServiceImpl(UsuarioRepository repository, UsuarioMapper mapper){
        this.repository=repository;
        this.mapper=mapper;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Pageable pageable, String search) {
        Page<Usuario> usuarios = repository.findAll(pageable);

        if(search==null || search.trim().isEmpty()){
            usuarios= repository.findAll(pageable);
        }else{
            usuarios= repository.findByEmailContainingIgnoreCase(pageable, search);
        }
        return new PageImpl<>(
              usuarios.getContent().stream()
                      .map(mapper::toDTO)
                      .collect(Collectors.toList()),
                pageable,
                usuarios.getTotalElements()
        );
      //  getContent() devuelve la lista de entidades Producto de la página actual.
        //  Luego, se utiliza stream() para procesar cada entidad, mapeándola a un DTO utilizando
        //  el método toDTO del mapper. Finalmente, se recopilan los DTOs
        //  en una lista con collect(Collectors.toList()) y se crea un nuevo PageImpl con esa lista,
        //  el pageable original y el total de elementos.
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO findById(Integer id) {
        // Evaluar bien esto...
        Usuario entidad= repository.findById(id).orElseThrow(
                ()->new NoDataFoundException("No existe un registro con  el ID ."));
        return mapper.toDTO(entidad);
    }

    @Override
    public UsuarioDTO create(UsuarioDTO obj) {
        UsuarioValidator.save(obj);
        if(repository.findByEmail(obj.getEmail()).isPresent()){
            throw new ValidateException("El email ya existe en el sistema, No procede.");
        }

        Usuario entidad=mapper.toEntity(obj);
        Usuario saved=repository.save(entidad);
        return mapper.toDTO(saved);
    }

    @Override
    public UsuarioDTO update(Integer id, UsuarioDTO obj) {
        UsuarioValidator.save(obj);
        Usuario entidad=mapper.toEntity(obj);
        if(repository.existsById(id)){
            entidad.setId(id);
            Usuario saved=repository.save(entidad);
            return mapper.toDTO(saved);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        Usuario entidad=repository.findById(id).orElseThrow(
                ()->new NoDataFoundException("No existe un registro con el ID."));
        repository.delete(entidad);
    }



} // END CLASS
