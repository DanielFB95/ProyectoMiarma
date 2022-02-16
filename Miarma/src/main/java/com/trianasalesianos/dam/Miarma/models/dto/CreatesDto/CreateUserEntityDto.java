package com.trianasalesianos.dam.Miarma.models.dto.CreatesDto;

import lombok.*;

import java.time.LocalDate;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserEntityDto {

    private String nick;
    private String email;
    private LocalDate fechaNacimiento;
    private boolean publicProfile;
    private String password;
    private String password2;


}
