package com.menuadmin.apipedidos.mapper;

import com.menuadmin.apipedidos.dto.PedidoDTO;
import com.menuadmin.apipedidos.entity.Cliente;
import com.menuadmin.apipedidos.entity.Pedido;
import com.menuadmin.apipedidos.entity.TipoComprobante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoMapper extends GenericMapper<Pedido, PedidoDTO> {
    @Autowired // Hago inyección de dependencias
    private PedidoDetalleMapper detalleMapper;

    @Override
    public PedidoDTO toDTO(Pedido entity) {
        if(entity== null) {
            return null;
        }
        return PedidoDTO.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .clienteId(entity.getCliente() != null ? entity.getCliente().getId() : null)
                .clienteNombre(entity.getCliente() != null ? entity.getCliente().getNombre() : null)
                .tipoComprobanteCodigo(entity.getTipoComprobante() != null ? entity.getTipoComprobante().getCodigo() : null)
                .tipoComprobanteDescripcion(entity.getTipoComprobante() != null ? entity.getTipoComprobante().getDescripcion() : null)
                .serie(entity.getSerie())
                .correlativo(entity.getCorrelativo())
                .total(entity.getTotal())
                .detalles(entity.getDetalles() != null
                        ? entity.getDetalles().stream()
                        .map(detalleMapper::toDTO)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }

    @Override
    public Pedido toEntity(PedidoDTO dto) {
        if(dto==null) {
            return null;
        }
        /*
            .builder() se utiliza para implementar
            el Patrón de Diseño Builder, cuyo objetivo es facilitar la creación
            de objetos complejos de una manera limpia y legible.
         */
        Pedido pedido = Pedido.builder()
                .id(dto.getId())
                .serie(dto.getSerie())
                .correlativo(dto.getCorrelativo())
                .total(dto.getTotal())
                .build();
        if (dto.getClienteId() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(dto.getClienteId());
            pedido.setCliente(cliente);
        }
        if (dto.getTipoComprobanteCodigo() != null) {
            TipoComprobante tipo = new TipoComprobante();
            tipo.setCodigo(dto.getTipoComprobanteCodigo());
            pedido.setTipoComprobante(tipo);
        }
        if (dto.getDetalles() != null) {
            pedido.setDetalles(dto.getDetalles().stream()
                    .map(detalleMapper::toEntity)
                    .collect(Collectors.toList()));
            pedido.getDetalles().forEach(d -> d.setPedido(pedido)); // mantener relación bidireccional
        }
        return pedido;
    }
}
