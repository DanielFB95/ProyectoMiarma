package com.trianasalesianos.dam.Miarma.models.dto.ConvertersDto;

import com.trianasalesianos.dam.Miarma.models.Publicacion;
import com.trianasalesianos.dam.Miarma.models.dto.CreatesDto.CreatePublicacionDto;
import com.trianasalesianos.dam.Miarma.models.dto.GetsDto.GetPublicacionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublicacionDtoConverter {

    private final UserEntityDtoConverter userEntityDtoConverter;

    public Publicacion createPublicacionDtoToPublicacion(CreatePublicacionDto createPublicacionDto){
        return Publicacion.builder()
                .texto(createPublicacionDto.getTexto())
                .titulo(createPublicacionDto.getTitulo())
                .usuario(createPublicacionDto.getUser())
                .publicPost(createPublicacionDto.isPublicPost())
                .build();
    }

    public GetPublicacionDto publicacionToGetPublicacionDto(Publicacion publicacion){
        return GetPublicacionDto.builder()
                .titulo(publicacion.getTitulo())
                .texto(publicacion.getTexto())
                .publicPost(publicacion.isPublicPost())
                .user(userEntityDtoConverter.UserEntityToGetUserEntityDto(publicacion.getUsuario()))
                .build();
    }

}
