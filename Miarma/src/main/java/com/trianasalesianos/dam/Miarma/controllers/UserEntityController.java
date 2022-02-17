package com.trianasalesianos.dam.Miarma.controllers;

import com.trianasalesianos.dam.Miarma.models.UserEntity;
import com.trianasalesianos.dam.Miarma.models.dto.ConvertersDto.UserEntityDtoConverter;
import com.trianasalesianos.dam.Miarma.models.dto.CreatesDto.CreateUserEntityDto;
import com.trianasalesianos.dam.Miarma.models.dto.GetsDto.GetUserEntityDto;
import com.trianasalesianos.dam.Miarma.services.UserEntityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Tag(name = "User", description = "Controller de usuario")
public class UserEntityController {

    private final UserEntityService userEntityService;
    private final UserEntityDtoConverter userEntityDtoConverter;

    @GetMapping("/{id}")
    public ResponseEntity<GetUserEntityDto> findOne(@PathVariable UUID id, @AuthenticationPrincipal UserEntity userEntity){

        return ResponseEntity.ok().body(userEntityDtoConverter.UserEntityToGetUserEntityDto(userEntityService.findOne(id,userEntity)));
    }

    @PutMapping("/me")
    public ResponseEntity<GetUserEntityDto> modificarUsuario(@AuthenticationPrincipal UserEntity userEntity,
                                                             @RequestPart("user") CreateUserEntityDto createUserEntityDto,
                                                             @RequestPart("file") MultipartFile file){
        return ResponseEntity.ok()
                .body(userEntityDtoConverter.UserEntityToGetUserEntityDto(userEntityService.modificarUsuario(userEntity,createUserEntityDto,file)));
    }

}
