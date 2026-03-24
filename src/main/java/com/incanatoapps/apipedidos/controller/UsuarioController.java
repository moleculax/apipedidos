package com.incanatoapps.apipedidos.controller;

import com.incanatoapps.apipedidos.dto.ApiResponse;
import com.incanatoapps.apipedidos.dto.UsuarioDTO;
import com.incanatoapps.apipedidos.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service=service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UsuarioDTO>>> findAll(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
    ){
        Pageable pageable= PageRequest.of(pageNumber, pageSize);
        Page<UsuarioDTO> page= service.findAll(pageable, search);
        return new ApiResponse<>(page, true, "success").createResponse(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> findById(@PathVariable Integer id) {
        UsuarioDTO dto=service.findById(id);
        return new ApiResponse<>(dto, true, "success").createResponse(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioDTO>> create(@Valid @RequestBody UsuarioDTO obj){
        UsuarioDTO created=service.create(obj);
        return new ApiResponse<>(created, true, "success").createResponse(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> update(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO obj) {
        UsuarioDTO edited=service.update(id, obj);
        return new ApiResponse<>(edited, true, "success").createResponse(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return new ApiResponse<Void>(null, true, "success").createResponse(HttpStatus.OK);
    }
}