package com.menuadmin.apipedidos.config;

import com.menuadmin.apipedidos.entity.TipoComprobante;
import com.menuadmin.apipedidos.entity.Unidad;
import com.menuadmin.apipedidos.repository.TipoComprobanteRepository;
import com.menuadmin.apipedidos.repository.UnidadRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initData(TipoComprobanteRepository tipoRepo, UnidadRepository unidadRepo) {
        return args -> {
            // ===== Tipos de Comprobante =====
            insertarTipoComprobante(tipoRepo, "00", "Otros");
            insertarTipoComprobante(tipoRepo, "01", "Factura");
            insertarTipoComprobante(tipoRepo, "03", "Boleta de Venta");
            insertarTipoComprobante(tipoRepo, "07", "Nota de Crédito");
            insertarTipoComprobante(tipoRepo, "08", "Nota de Débito");
            insertarTipoComprobante(tipoRepo, "09", "Guía de Remisión - Remitente");

            // ===== Unidades de ejemplo =====
            insertarUnidad(unidadRepo, "KG", "Kilogramo");
            insertarUnidad(unidadRepo, "LTR", "Litro");
            insertarUnidad(unidadRepo, "BO", "Botella");
            insertarUnidad(unidadRepo, "BX", "Caja");
        };
    }

    private void insertarTipoComprobante(TipoComprobanteRepository repo, String codigo, String descripcion) {
        repo.findByCodigo(codigo).orElseGet(() ->
                repo.save(TipoComprobante.builder()
                        .codigo(codigo)
                        .descripcion(descripcion)
                        .build())
        );
    }

    private void insertarUnidad(UnidadRepository repo, String id, String descripcion) {
        if (!repo.existsById(id)) {
            repo.save(Unidad.builder()
                    .id(id)
                    .descripcion(descripcion)
                    .build());
        }
    }

}
