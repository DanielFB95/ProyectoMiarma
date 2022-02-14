package com.trianasalesianos.dam.Miarma.models.dto.CreatesDto;

import com.trianasalesianos.dam.Miarma.models.FileResponse;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserEntityDto {

    private String nick;
    private String email;
    private LocalDateTime fechaNacimiento;
    private boolean publicProfile;
    private FileResponse avatar;
    private String password;
    private String password2;


}
