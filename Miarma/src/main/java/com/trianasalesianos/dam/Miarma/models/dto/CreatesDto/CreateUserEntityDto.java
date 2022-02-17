package com.trianasalesianos.dam.Miarma.models.dto.CreatesDto;

import com.trianasalesianos.dam.Miarma.validacion.anotaciones.NameUnique;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserEntityDto {

    private String nick;
    @NotNull
    @NameUnique
    private String email;
    private LocalDate fechaNacimiento;
    private boolean publicProfile;
    @NotNull
    private String password;
    @NotNull
    private String password2;


}
