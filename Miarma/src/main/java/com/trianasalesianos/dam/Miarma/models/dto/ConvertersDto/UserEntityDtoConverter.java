package com.trianasalesianos.dam.Miarma.models.dto.ConvertersDto;

import com.trianasalesianos.dam.Miarma.models.UserEntity;
import com.trianasalesianos.dam.Miarma.models.dto.GetsDto.GetUserEntityDto;
import org.springframework.stereotype.Component;

@Component
public class UserEntityDtoConverter {

    public GetUserEntityDto UserEntityToGetUserEntityDto(UserEntity userEntity){
        return GetUserEntityDto.builder()
                .nick(userEntity.getNick())
                .email(userEntity.getEmail())
                .fechaNacimiento(userEntity.getFechaNacimiento())
                .avatar(userEntity.getUrlAvatar())
                .password(userEntity.getPassword())
                .build();

    }
}
