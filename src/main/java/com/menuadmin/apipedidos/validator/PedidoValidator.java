package com.menuadmin.apipedidos.validator;

import com.menuadmin.apipedidos.dto.PedidoDTO;
import com.menuadmin.apipedidos.dto.PedidoDetalleDTO;
import com.menuadmin.apipedidos.exception.ValidateException;

import java.math.BigDecimal;

public class PedidoValidator {
    public static void save(PedidoDTO pedido){
        if (pedido == null) {
            throw new ValidateException("El pedido no puede ser nulo");
        }
        // Validar cliente
        if (pedido.getClienteId() == null) {
            throw new ValidateException("El cliente es requerido");
        }

        // Validar tipo de comprobante
        if (pedido.getTipoComprobanteCodigo() == null || pedido.getTipoComprobanteCodigo().trim().isEmpty()) {
            throw new ValidateException("El tipo de comprobante es requerido");
        }
        if (pedido.getSerie() == null || pedido.getSerie().trim().isEmpty()) {
            throw new ValidateException("La serie es requerida");
        }
        if (pedido.getCorrelativo()<=0) {
            throw new ValidateException("El correlativo es requerido");
        }

        // Validar total
        //if (pedido.getTotal() == null || pedido.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
        //    throw new ValidateException("El total del pedido debe ser mayor a cero");
        //}

        // Validar detalles
        if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
            throw new ValidateException("El pedido debe tener al menos un detalle");
        }
        for (PedidoDetalleDTO detalle : pedido.getDetalles()) {
            validateDetalle(detalle);
        }
    }

    private static void validateDetalle(PedidoDetalleDTO detalle) {
        if (detalle.getProductoId() == null) {
            throw new ValidateException("El producto es requerido en cada detalle");
        }
        if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
            throw new ValidateException("La cantidad debe ser mayor a cero en cada detalle");
        }
        if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidateException("El precio unitario debe ser mayor a cero en cada detalle");
        }
        if (detalle.getSubtotal() == null || detalle.getSubtotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidateException("El subtotal debe ser mayor a cero en cada detalle");
        }

        // Validar consistencia: subtotal = cantidad * precioUnitario
        BigDecimal expectedSubtotal = detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));
        if (detalle.getSubtotal().compareTo(expectedSubtotal) != 0) {
            throw new ValidateException("El subtotal del detalle no coincide con cantidad * precio unitario");
        }
    }

}
