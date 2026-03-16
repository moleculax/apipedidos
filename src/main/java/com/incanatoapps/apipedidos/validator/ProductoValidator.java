package com.incanatoapps.apipedidos.validator;

import com.incanatoapps.apipedidos.dto.ProductoDTO;
import com.incanatoapps.apipedidos.exception.ValidateException;

public class ProductoValidator {
    public static void save(ProductoDTO registro) {
        if (registro.getNombre() == null || registro.getNombre().trim().isEmpty()) {
            throw new ValidateException("El nombre del producto es requerido");
        }
        if (registro.getUnidadId() == null || registro.getUnidadId().trim().isEmpty()) {
            throw new ValidateException("La Unidad es requerida");
        }
        if (registro.getNombre().trim().length() > 70) {
            throw new ValidateException("El nombre del producto no debe exceder los 70 caracteres");
        }
        if (registro.getPrecioUnitario() == null || registro.getPrecioUnitario().doubleValue() < 0) {
            throw new ValidateException("El precio unitario debe ser mayor o igual que cero");
        }
    }
}
