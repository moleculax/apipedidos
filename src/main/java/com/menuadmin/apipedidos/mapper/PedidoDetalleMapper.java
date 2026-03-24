package com.menuadmin.apipedidos.mapper;

import com.menuadmin.apipedidos.dto.PedidoDetalleDTO;
import com.menuadmin.apipedidos.entity.PedidoDetalle;
import com.menuadmin.apipedidos.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class PedidoDetalleMapper extends GenericMapper<PedidoDetalle, PedidoDetalleDTO> {

    @Override
    public PedidoDetalleDTO toDTO(PedidoDetalle entity) {
        if(entity ==null) return null;
        return PedidoDetalleDTO.builder()
                .id(entity.getId())
                .productoId(entity.getProducto() != null ? entity.getProducto().getId() : null)
                .productoNombre(entity.getProducto() != null ? entity.getProducto().getNombre() : null)
                .cantidad(entity.getCantidad())
                .precioUnitario(entity.getPrecioUnitario())
                .subtotal(entity.getSubtotal())
                .build();
    }

    @Override
    public PedidoDetalle toEntity(PedidoDetalleDTO dto) {
        if(dto==null) return null;

        PedidoDetalle detalle= PedidoDetalle.builder()
                .id(dto.getId())
                .cantidad(dto.getCantidad())
                .precioUnitario(dto.getPrecioUnitario())
                .subtotal(dto.getSubtotal())
                .build();
        if(dto.getProductoId() != null){
            Producto producto=new Producto();
            producto.setId(dto.getProductoId());
            detalle.setProducto(producto);
        }
        return detalle;
    }
}
