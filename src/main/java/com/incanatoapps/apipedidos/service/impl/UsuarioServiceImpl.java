package com.incanatoapps.apipedidos.service.impl;

import com.incanatoapps.apipedidos.dto.UsuarioDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository repository, UsuarioMapper mapper, PasswordEncoder passwordEncoder){
        this.repository=repository;
        this.mapper=mapper;
        this.passwordEncoder=passwordEncoder;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Pageable pageable, String search) {
        Page<Usuario> usuarios;
        //Alt+ 124
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
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO findById(Integer id) {
        Usuario entidad= repository.findById(id).orElseThrow(
                ()->new NoDataFoundException("No existe un registro con ese ID."));
        return mapper.toDTO(entidad);
    }

    @Override
    public UsuarioDTO create(UsuarioDTO obj) {
        UsuarioValidator.save(obj);
        if (repository.findByEmail(obj.getEmail()).isPresent())          {
            throw new ValidateException("El email ya está registrado");
        }
        Usuario usuario=mapper.toEntity(obj);
        usuario.setPassword(passwordEncoder.encode(obj.getPassword()));
        Usuario saved=repository.save(usuario);
        return mapper.toDTO(saved);
    }

    @Override
    public UsuarioDTO update(Integer id, UsuarioDTO obj) {
        UsuarioValidator.save(obj);

        Usuario usuarioActual = repository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("No existe un registro con ese ID"));

        Usuario entidad = mapper.toEntity(obj);

        // Actualizar campos directamente en la entidad existente
        usuarioActual.setEmail(entidad.getEmail());
        usuarioActual.setActivo(entidad.isActivo());
        usuarioActual.setRoles(entidad.getRoles());

        if (obj.getPassword() != null && !obj.getPassword().isEmpty()) {
            usuarioActual.setPassword(passwordEncoder.encode(obj.getPassword()));
        }

        Usuario saved = repository.save(usuarioActual);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Integer id) {
        Usuario entidad=repository.findById(id).orElseThrow(
                ()->new NoDataFoundException("No existe un registro con ese ID."));
        repository.delete(entidad);
    }
}