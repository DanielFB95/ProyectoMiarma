package com.trianasalesianos.dam.Miarma.security.dto;

import com.trianasalesianos.dam.Miarma.models.Publicacion;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String nombre;
    private String avatar;
    private String email;
    private LocalDate fechaNacimiento;
    private String token;
    private List<Publicacion> lista;
}
