package com.trianasalesianos.dam.Miarma.models.dto.CreatesDto;

import com.trianasalesianos.dam.Miarma.models.UserEntity;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePublicacionDto {

    private String titulo;
    private String texto;
    private boolean publicPost;
    private String userEmail;

}
