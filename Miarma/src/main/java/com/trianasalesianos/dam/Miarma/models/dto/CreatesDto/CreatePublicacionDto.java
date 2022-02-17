package com.trianasalesianos.dam.Miarma.models.dto.CreatesDto;

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
