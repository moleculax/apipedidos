package com.menuadmin.apipedidos.mapper;

import com.menuadmin.apipedidos.dto.ProductoDTO;
import com.menuadmin.apipedidos.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper extends GenericMapper<Producto, ProductoDTO>{
    @Override
    public ProductoDTO toDTO(Producto entity) {
        if (entity == null) {
            return null;
        }
        return ProductoDTO.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .nombre(entity.getNombre())
                .precioUnitario(entity.getPrecioUnitario())
                .unidadId(entity.getUnidadId())
                .unidadNombre(entity.getUnidad() != null? entity.getUnidad().getDescripcion():null)
                .build();
    }

    @Override
    public Producto toEntity(ProductoDTO dto) {
        if(dto==null){
            return null;
        }
        return Producto.builder()
                .id(dto.getId())
                .codigo(dto.getCodigo())
                .nombre(dto.getNombre())
                .precioUnitario(dto.getPrecioUnitario())
                .unidadId(dto.getUnidadId())
                .build();
    }
}
