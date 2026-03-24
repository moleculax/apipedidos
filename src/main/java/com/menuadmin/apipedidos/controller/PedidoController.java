package com.menuadmin.apipedidos.controller;

import com.menuadmin.apipedidos.dto.ApiResponse;
import com.menuadmin.apipedidos.dto.PedidoDTO;
import com.menuadmin.apipedidos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("api/pedidos")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service){
        this.service=service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PedidoDTO>>> findAll(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
    ){
        Pageable pageable= PageRequest.of(pageNumber, pageSize);
        Page<PedidoDTO> page= service.findAll(pageable, search);
        return new ApiResponse<>(page, true, "success").createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoDTO>> findById(@PathVariable Long id) {
        PedidoDTO dto=service.findById(id);
        return new ApiResponse<>(dto, true, "success").createResponse(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PedidoDTO>> create(@Valid @RequestBody PedidoDTO obj){
        PedidoDTO created=service.create(obj);
        return new ApiResponse<>(created, true, "success").createResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoDTO>> update(@PathVariable Long id, @Valid @RequestBody PedidoDTO obj) {
        PedidoDTO edited=service.update(id, obj);
        return new ApiResponse<>(edited, true, "success").createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return new ApiResponse<Void>(null, true, "success").createResponse(HttpStatus.OK);
    }
}
