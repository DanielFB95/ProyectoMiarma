package com.trianasalesianos.dam.Miarma.errors.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ApiError {

    private HttpStatus status;
    private int code;
    private String message;
    private String route;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime date = LocalDateTime.now();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiSubError> subErrors;

    public ApiError(HttpStatus status,String message, String route) {
        this.status = status;
        this.message = message;
        this.route = route;
        this.code = status.value();
        this.date = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String message, String route, List<ApiSubError> subErrors){
        this(status,message,route);
        this.subErrors = subErrors;
    }
}

