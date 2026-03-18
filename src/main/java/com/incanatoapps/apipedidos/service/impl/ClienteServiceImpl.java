package com.incanatoapps.apipedidos.service.impl;

import com.incanatoapps.apipedidos.dto.ClienteDTO;
import com.incanatoapps.apipedidos.dto.ProductoDTO;
import com.incanatoapps.apipedidos.entity.Cliente;
import com.incanatoapps.apipedidos.entity.Producto;
import com.incanatoapps.apipedidos.exception.NoDataFoundException;
import com.incanatoapps.apipedidos.mapper.ClienteMapper;
import com.incanatoapps.apipedidos.repository.ClienteRepository;
import com.incanatoapps.apipedidos.service.ClienteService;
import com.incanatoapps.apipedidos.validator.ClienteValidator;
import com.incanatoapps.apipedidos.validator.ProductoValidator;
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

        if(search==null || search.trim().isEmpty()){
            clientes= repository.findAll(pageable);
        }else{
            clientes= repository.findByNumeroDocumentoContainingIgnoreCase(pageable, search);
        }
        return new PageImpl<>(
              clientes.getContent().stream()
                      .map(mapper::toDTO)
                      .collect(Collectors.toList()),
                pageable,
                clientes.getTotalElements()
        );
      //  getContent() devuelve la lista de entidades Producto de la página actual.
        //  Luego, se utiliza stream() para procesar cada entidad, mapeándola a un DTO utilizando
        //  el método toDTO del mapper. Finalmente, se recopilan los DTOs
        //  en una lista con collect(Collectors.toList()) y se crea un nuevo PageImpl con esa lista,
        //  el pageable original y el total de elementos.
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id) {
        // Evaluar bien esto...
        Cliente entidad= repository.findById(id).orElseThrow(
                ()->new NoDataFoundException("No existe un registro con  el ID ."));
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
                ()->new NoDataFoundException("No existe un registro con el ID."));
        repository.delete(entidad);
    }


    @Override
    public ClienteDTO findByNumeroDocumento(String numeroDocumento) {
        return null;
    }
}
