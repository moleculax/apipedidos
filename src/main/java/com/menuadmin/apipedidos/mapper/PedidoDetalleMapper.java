package com.menuadmin.apipedidos.mapper;

import com.menuadmin.apipedidos.dto.PedidoDetalleDTO;
import com.menuadmin.apipedidos.entity.PedidoDetalle;
import com.menuadmin.apipedidos.entity.Producto;
import org.springframework.stereotype.Component;

@Component

//PedidoDetalle ->Entidad
// AQUI PASAMOS DE entidad a DTO y DTO a entidad  DE PEDIDO DETALLE
public class PedidoDetalleMapper extends GenericMapper<PedidoDetalle, PedidoDetalleDTO> {
// GenericMapper:   se usa para convertir, por ejemplo,
// una Entidad de Base de Datos en un DTO (Data Transfer Object) sin tener que escribir código repetitivo para cada propiedad.
    @Override
    public PedidoDetalleDTO toDTO(PedidoDetalle entity) {
        if(entity == null){
            return null;
        }
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
        if(dto == null) {
            return null;
        }

        PedidoDetalle detalle= PedidoDetalle.builder()
                .id(dto.getId())
                .cantidad(dto.getCantidad())
                .precioUnitario(dto.getPrecioUnitario())
                .subtotal(dto.getSubtotal())
                .build();
        if(dto.getProductoId() != null){
            Producto producto = new Producto();
            producto.setId(dto.getProductoId());
            detalle.setProducto(producto);
        }
        return detalle;
    }
}
