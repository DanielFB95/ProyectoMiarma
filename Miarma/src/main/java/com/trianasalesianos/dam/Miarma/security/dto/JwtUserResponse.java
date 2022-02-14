package com.trianasalesianos.dam.Miarma.security.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String avatar;
    private String email;
    private String rol;
    private String token;
}
