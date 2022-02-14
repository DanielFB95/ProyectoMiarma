package com.trianasalesianos.dam.Miarma.models.dto.GetsDto;

import com.trianasalesianos.dam.Miarma.models.FileResponse;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetUserEntityDto {

    private String nick;
    private String email;
    private LocalDateTime fechaNacimiento;
    private String avatar;

}
