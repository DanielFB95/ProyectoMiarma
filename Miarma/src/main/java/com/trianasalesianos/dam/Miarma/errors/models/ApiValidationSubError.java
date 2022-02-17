package com.trianasalesianos.dam.Miarma.errors.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ApiValidationSubError extends ApiSubError{

    private String object;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object objectError;
}
