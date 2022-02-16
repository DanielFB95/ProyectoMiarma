package com.trianasalesianos.dam.Miarma.security.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String nombre;
    private String avatar;
    private String email;
    private LocalDate fechaNacimiento;
    private String token;
}
