package com.incanatoapps.apipedidos.service.impl;

import com.incanatoapps.apipedidos.dto.ProductoDTO;
import com.incanatoapps.apipedidos.entity.Producto;
import com.incanatoapps.apipedidos.exception.NoDataFoundException;
import com.incanatoapps.apipedidos.mapper.ProductoMapper;
import com.incanatoapps.apipedidos.repository.ProductoRepository;
import com.incanatoapps.apipedidos.service.ProductoService;
import com.incanatoapps.apipedidos.validator.ProductoValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository repository;
    private final ProductoMapper mapper;

    public ProductoServiceImpl(ProductoRepository repository, ProductoMapper mapper){
        this.repository=repository;
        this.mapper=mapper;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> findAll(Pageable pageable, String search) {
        Page<Producto> productos;

        if(search==null || search.trim().isEmpty()){
            productos= repository.findAll(pageable);
        }else{
            productos= repository.findByNombreContainingIgnoreCase(pageable, search);
        }
        return new PageImpl<>(
              productos.getContent().stream()
                      .map(mapper::toDTO)
                      .collect(Collectors.toList()),
                pageable,
                productos.getTotalElements()
        );
      //  getContent() devuelve la lista de entidades Producto de la página actual.
        //  Luego, se utiliza stream() para procesar cada entidad, mapeándola a un DTO utilizando
        //  el método toDTO del mapper. Finalmente, se recopilan los DTOs
        //  en una lista con collect(Collectors.toList()) y se crea un nuevo PageImpl con esa lista,
        //  el pageable original y el total de elementos.
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO findById(Long id) {
        // Evaluar bi esto...
        Producto entidad= repository.findById(id).orElseThrow(
                ()->new NoDataFoundException("No existe un registro con  el ID ."));
        return mapper.toDTO(entidad);
    }

    @Override
    public ProductoDTO create(ProductoDTO obj) {
        ProductoValidator.save(obj);
        Producto entidad=mapper.toEntity(obj);
        Producto saved=repository.save(entidad);
        return mapper.toDTO(saved);
    }

    @Override
    public ProductoDTO update(Long id, ProductoDTO obj) {
        ProductoValidator.save(obj);
        Producto entidad=mapper.toEntity(obj);
        if(repository.existsById(id)){
            entidad.setId(id);
            Producto saved=repository.save(entidad);
            return mapper.toDTO(saved);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Producto entidad=repository.findById(id).orElseThrow(
                ()->new NoDataFoundException("No existe un registro con el ID."));
        repository.delete(entidad);
    }
}
