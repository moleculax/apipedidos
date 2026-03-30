package com.menuadmin.apipedidos.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/*
    La anotación @Data es parte de la librería Lombok y se usa para eliminar el "código basura"
    (boilerplate) de tus clases Java, generando automáticamente los métodos más comunes.

    @Builder sobre una clase, Lombok genera una clase interna estática (el "Constructor")
    que contiene métodos para asignar valor a cada atributo y un
    método .build() para finalizar la creación del objeto.
 */

@Data
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
