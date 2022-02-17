package com.trianasalesianos.dam.Miarma.models.dto.GetsDto;

import com.trianasalesianos.dam.Miarma.models.FileResponse;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetUserEntityDto {

    private String nick;
    private String email;
    private LocalDate fechaNacimiento;
    private String avatar;
    private String password;

}
