package com.trianasalesianos.dam.Miarma.models.dto.GetsDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetPublicacionDto {

    private String titulo;
    private String texto;
    private boolean publicPost;
    private GetUserEntityDto user;

}
