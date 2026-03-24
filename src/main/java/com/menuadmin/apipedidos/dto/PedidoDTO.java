package com.menuadmin.apipedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Long id;
    private LocalDate fecha;
    private Long clienteId;
    private String clienteNombre;
    private String tipoComprobanteCodigo;
    private String tipoComprobanteDescripcion;
    private String serie;
    private int correlativo;
    private BigDecimal total;
    private List<PedidoDetalleDTO> detalles; // lista de detalles
}
