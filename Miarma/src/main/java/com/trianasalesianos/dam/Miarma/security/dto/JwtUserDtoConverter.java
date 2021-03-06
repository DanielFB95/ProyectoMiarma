package com.trianasalesianos.dam.Miarma.security.dto;

import com.trianasalesianos.dam.Miarma.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDtoConverter {

    public JwtUserResponse userToJwtUserResponse(UserEntity user, String jwt){
        return JwtUserResponse.builder()
                .nombre(user.getNick())
                .email(user.getEmail())
                .avatar(user.getUrlAvatar())
                .email(user.getEmail())
                .fechaNacimiento(user.getFechaNacimiento())
                .lista(user.getListaPublicaciones())
                .token(jwt)
                .build();
    }
}
