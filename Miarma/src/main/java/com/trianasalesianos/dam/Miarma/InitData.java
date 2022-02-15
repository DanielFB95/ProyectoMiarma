package com.trianasalesianos.dam.Miarma;

import com.trianasalesianos.dam.Miarma.models.UserEntity;
import com.trianasalesianos.dam.Miarma.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitData {

    private final UserEntityService userEntityService;

    @PostConstruct
    public void init(){


        userEntityService.save(UserEntity.builder()
                        .email("fernandez.bedan20@triana.salesianos.edu")
                        .password("12345")
                        .fechaNacimiento(LocalDateTime.now())
                        .nick("DanielFB")
                .build());
    }
}
