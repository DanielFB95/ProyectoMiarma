package com.trianasalesianos.dam.Miarma;

import com.trianasalesianos.dam.Miarma.models.UserEntity;
import com.trianasalesianos.dam.Miarma.models.dto.CreatesDto.CreatePublicacionDto;
import com.trianasalesianos.dam.Miarma.repositories.PublicacionRepository;
import com.trianasalesianos.dam.Miarma.services.PublicacionService;
import com.trianasalesianos.dam.Miarma.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitData {

    private final UserEntityService userEntityService;
    private final PublicacionService publicacionRepository;


    @PostConstruct
    public void init(){

        /*
        userEntityService.save(UserEntity.builder()
                        .email("fernandez.bedan20@triana.salesianos.edu")
                        .password("12345")
                        .fechaNacimiento(LocalDate.now())
                        .nick("DanielFB")
                .build());
         */

    }


}
