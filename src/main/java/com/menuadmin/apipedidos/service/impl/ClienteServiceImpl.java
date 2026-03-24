package com.menuadmin.apipedidos.service.impl;

import com.menuadmin.apipedidos.dto.ClienteDTO;
import com.menuadmin.apipedidos.entity.Cliente;
import com.menuadmin.apipedidos.exception.NoDataFoundException;
import com.menuadmin.apipedidos.mapper.ClienteMapper;
import com.menuadmin.apipedidos.repository.ClienteRepository;
import com.menuadmin.apipedidos.service.ClienteService;
import com.menuadmin.apipedidos.validator.ClienteValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public ClienteServiceImpl(ClienteRepository repository, ClienteMapper mapper){
        this.repository=repository;
        this.mapper=mapper;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAll(Pageable pageable, String search) {
        Page<Cliente> clientes;
        //Alt+ 124
        if(search==null || search.trim().isEmpty()){
            clientes= repository.findAll(pageable);
        }else{
            clientes= repository.findByNombreContainingIgnoreCase(pageable, search);
        }
        return new PageImpl<>(
              clientes.getContent().stream()
                      .map(mapper::toDTO)
                      .collect(Collectors.toList()),
                pageable,
                clientes.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id) {
        Cliente entidad= repository.findById(id).orElseThrow(
                ()->new NoDataFoundException("No existe un registro con ese ID."));
        return mapper.toDTO(entidad);
    }

    @Override
    public ClienteDTO create(ClienteDTO obj) {
        ClienteValidator.save(obj);
        Cliente entidad=mapper.toEntity(obj);
        Cliente saved=repository.save(entidad);
        return mapper.toDTO(saved);
    }

    @Override
    public ClienteDTO update(Long id, ClienteDTO obj) {
        ClienteValidator.save(obj);
        Cliente entidad=mapper.toEntity(obj);
        if(repository.existsById(id)){
            entidad.setId(id);
            Cliente saved=repository.save(entidad);
            return mapper.toDTO(saved);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Cliente entidad=repository.findById(id).orElseThrow(
                ()->new NoDataFoundException("No existe un registro con ese ID."));
        repository.delete(entidad);
    }
}
