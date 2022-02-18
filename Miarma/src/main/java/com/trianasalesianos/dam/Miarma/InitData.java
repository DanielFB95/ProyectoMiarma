package com.trianasalesianos.dam.Miarma;

import com.trianasalesianos.dam.Miarma.models.UserEntity;
import com.trianasalesianos.dam.Miarma.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitData {

    private final UserEntityService userEntityService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){

        /*
        UserEntity user = UserEntity.builder()
                .nick("DanielFB")
                .email("fernandez.bedan20@triana.salesianos.edu")
                .password("12345")
                .publicProfile(true)
                .build();

        userEntityService.save(user);

         */
    }
}
