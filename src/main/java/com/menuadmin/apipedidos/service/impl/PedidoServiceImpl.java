package com.menuadmin.apipedidos.service.impl;

import com.menuadmin.apipedidos.dto.PedidoDTO;
import com.menuadmin.apipedidos.entity.Pedido;
import com.menuadmin.apipedidos.exception.NoDataFoundException;
import com.menuadmin.apipedidos.mapper.PedidoMapper;
import com.menuadmin.apipedidos.repository.PedidoRepository;
import com.menuadmin.apipedidos.service.PedidoService;
import com.menuadmin.apipedidos.validator.PedidoValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository repository;
    private final PedidoMapper mapper;

    public PedidoServiceImpl(PedidoRepository repository, PedidoMapper mapper){
        this.repository=repository;
        this.mapper=mapper;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<PedidoDTO> findAll(Pageable pageable, String search) {
        Page<Pedido> pedidos;
        //Alt+ 124
        if(search==null || search.trim().isEmpty()){
            pedidos= repository.findAll(pageable);
        }else{
            pedidos= repository.findByCorrelativoContainingIgnoreCase(pageable, search);
        }
        return new PageImpl<>(
              pedidos.getContent().stream()
                      .map(mapper::toDTO)
                      .collect(Collectors.toList()),
                pageable,
                pedidos.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoDTO findById(Long id) {
        Pedido entidad= repository.findById(id).orElseThrow(
                ()->new NoDataFoundException("No existe un registro con ese ID."));
        return mapper.toDTO(entidad);
    }

    @Override
    public PedidoDTO create(PedidoDTO obj) {
        PedidoValidator.save(obj);
        Pedido entidad = mapper.toEntity(obj);
        entidad.setFecha(LocalDate.now());
        BigDecimal total = obj.getDetalles().stream()
                .map(item -> item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        entidad.setTotal(total);

        Pedido saved=repository.save(entidad);
        return mapper.toDTO(saved);
    }

    @Override
    public PedidoDTO update(Long id, PedidoDTO obj) {
        PedidoValidator.save(obj);
        Pedido entidad=mapper.toEntity(obj);
        if(repository.existsById(id)){
            entidad.setId(id);
            BigDecimal total = obj.getDetalles().stream()
                    .map(item -> item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            entidad.setTotal(total);
            Pedido saved=repository.save(entidad);
            return mapper.toDTO(saved);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Pedido entidad=repository.findById(id).orElseThrow(
                ()->new NoDataFoundException("No existe un registro con ese ID."));
        repository.delete(entidad);
    }
}
