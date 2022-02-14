package com.trianasalesianos.dam.Miarma.security.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String nombre;
    private String avatar;
    private String email;
    private LocalDateTime fechaNacimiento;
    private String token;
}
