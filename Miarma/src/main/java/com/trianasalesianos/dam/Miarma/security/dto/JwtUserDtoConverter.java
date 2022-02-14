package com.trianasalesianos.dam.Miarma.security.dto;

import com.salesianostriana.dam.RealEstate.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDtoConverter {

    public JwtUserResponse userToJwtUserResponse(UserEntity user, String jwt){
        return JwtUserResponse.builder()
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .direccion(user.getDireccion())
                .telefono(user.getTelefono())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .rol(user.getRol().name())
                .token(jwt)
                .build();
    }
}
