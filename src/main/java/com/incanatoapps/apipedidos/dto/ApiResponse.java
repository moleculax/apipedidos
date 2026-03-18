package com.incanatoapps.apipedidos.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

/*
combina múltiples anotaciones en una sola para reducir la cantidad
de código repetitivo. En este caso, @Data genera automáticamente
 */
@Data
/*
La anotación @Builder de Lombok implementa el patrón de diseño Builder,
que es una forma de construir objetos complejos paso a paso.
 */
@Builder
public class ApiResponse <T>{
    private T data;
    private boolean ok;
    private String message;

    public ApiResponse(T data, boolean ok, String message){
        this.data=data;
        this.ok=ok;
        this.message=message;
    }

    public ResponseEntity<ApiResponse<T>> createResponse(){
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<T>> createResponse(HttpStatus status){
        return new ResponseEntity<>(this, status);
    }
}
