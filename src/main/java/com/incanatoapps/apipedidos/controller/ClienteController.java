package com.incanatoapps.apipedidos.controller;

import com.incanatoapps.apipedidos.dto.ApiResponse;
import com.incanatoapps.apipedidos.dto.ClienteDTO;
import com.incanatoapps.apipedidos.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service){

        this.service=service;

    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ClienteDTO>>> findAll(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
    ){
        Pageable pageable= PageRequest.of(pageNumber, pageSize);
        Page<ClienteDTO> page= service.findAll(pageable, search);
        return new ApiResponse<>(page, true, "success").createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteDTO>> findById(@PathVariable Long id) {
        ClienteDTO dto=service.findById(id);
        return new ApiResponse<>(dto, true, "success").createResponse(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteDTO>> create(@Valid @RequestBody ClienteDTO obj){
        ClienteDTO created=service.create(obj);
        return new ApiResponse<>(created, true, "success").createResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteDTO>> update(@PathVariable Long id, @Valid @RequestBody ClienteDTO obj) {
        ClienteDTO edited=service.update(id, obj);
        return new ApiResponse<>(edited, true, "success").createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return new ApiResponse<Void>(null, true, "success").createResponse(HttpStatus.OK);
    }
}
