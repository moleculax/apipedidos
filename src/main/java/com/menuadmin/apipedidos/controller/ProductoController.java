package com.menuadmin.apipedidos.controller;

import com.menuadmin.apipedidos.dto.WrapperResponse;
import com.menuadmin.apipedidos.dto.ProductoDTO;
import com.menuadmin.apipedidos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RequestMapping("api/productos")
@Tag(name = "Productos", description = "API de operaciones relacionadas con productos")
public class ProductoController {
    private final ProductoService service;

    public ProductoController(ProductoService service){
        this.service=service;
    }

    @GetMapping

    // ======== ESTO ES PARA LA DOCUMENTACION DE SWAGGER, PARA QUE APAREZCA LA DESCRIPCION DE CADA ENDPOINT ==============
    @Operation(
            summary = "Obtener lista de productos",
            description = "Permite obtener una lista paginada de productos. Se puede filtrar por nombre utilizando el parámetro de búsqueda."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
                    @ApiResponse(responseCode = "401", description = "No autorizado"),
                    @ApiResponse(responseCode = "403", description = "Prohibido"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )

    // ==============================================================================================================
    public ResponseEntity<WrapperResponse<Page<ProductoDTO>>> findAll(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
    ){
        Pageable pageable= PageRequest.of(pageNumber, pageSize);
        Page<ProductoDTO> page= service.findAll(pageable, search);
        return new WrapperResponse<>(page, true, "success").createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener producto por ID",
            description = "Permite obtener los detalles de un producto específico utilizando su ID."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto obtenido exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Solicitud inválida"
                    )
            }
    )
    public ResponseEntity<WrapperResponse<ProductoDTO>> findById(@PathVariable Long id) {
        ProductoDTO dto=service.findById(id);
        return new WrapperResponse<>(dto, true, "success").createResponse(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WrapperResponse<ProductoDTO>> create(@Valid @RequestBody ProductoDTO obj){
        ProductoDTO created=service.create(obj);
        return new WrapperResponse<>(created, true, "success").createResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WrapperResponse<ProductoDTO>> update(@PathVariable Long id, @Valid @RequestBody ProductoDTO obj) {
        ProductoDTO edited=service.update(id, obj);
        return new WrapperResponse<>(edited, true, "success").createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WrapperResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return new WrapperResponse<Void>(null, true, "success").createResponse(HttpStatus.OK);
    }
}
