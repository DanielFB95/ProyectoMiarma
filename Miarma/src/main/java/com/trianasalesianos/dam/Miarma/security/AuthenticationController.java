package com.trianasalesianos.dam.Miarma.security;

import com.salesianostriana.dam.RealEstate.models.Vivienda;
import com.salesianostriana.dam.RealEstate.security.dto.JwtUserDtoConverter;
import com.salesianostriana.dam.RealEstate.security.dto.LoginDto;
import com.salesianostriana.dam.RealEstate.security.jwt.JwtProvider;
import com.salesianostriana.dam.RealEstate.users.dto.CreateUserEntityDto;
import com.salesianostriana.dam.RealEstate.users.dto.GetUserEntityDto;
import com.salesianostriana.dam.RealEstate.users.dto.UserEntityDtoConverter;
import com.salesianostriana.dam.RealEstate.users.model.UserEntity;
import com.salesianostriana.dam.RealEstate.users.service.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name= "Authentication", description = "Controller authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final JwtUserDtoConverter jwtUserDtoConverter;
    private final UserEntityService userEntityService;
    private final UserEntityDtoConverter userEntityDtoConverter;


    @Operation(summary = "Login de un usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha podido loguear el usuario.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido loguear el usuario.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})
            ,
            @ApiResponse(responseCode = "401",
                    description = "No tiene permiso para realizar esta acción.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})})
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginDto loginDto){

        Authentication authentication =
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                    )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        UserEntity user = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jwtUserDtoConverter.userToJwtUserResponse(user,jwt));

    }


    @Operation(summary = "Obtiene el usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado el usuario.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el usuario.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})
            ,
            @ApiResponse(responseCode = "401",
                    description = "No tiene permiso para realizar esta acción.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})})
    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserEntity user){
        return ResponseEntity.ok(jwtUserDtoConverter.userToJwtUserResponse(user,null));
    }

    @Operation(summary = "Registra un nuevo propietario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han registrado un propietario.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha registrado ningún propietario.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})
            ,
            @ApiResponse(responseCode = "401",
                    description = "No tiene permiso para realizar esta acción.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})})
    @PostMapping("/register/user")
    public ResponseEntity<GetUserEntityDto> nuevoPropietario(@RequestBody CreateUserEntityDto nuevoPropietario){

        UserEntity user = userEntityService.savePropietario(nuevoPropietario);

        if(user == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(userEntityDtoConverter.UserEntityToGetUserEntityDto(user));
        }
    }


    @Operation(summary = "Registra un nuevo administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han registrado un administrador.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha registrado ningún administrador.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})
            ,
            @ApiResponse(responseCode = "401",
                    description = "No tiene permiso para realizar esta acción.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})})
    @PostMapping("/register/admin")
    public ResponseEntity<GetUserEntityDto> nuevoAdmin(@RequestBody CreateUserEntityDto nuevoAdmin){

        UserEntity user = userEntityService.saveAdmin(nuevoAdmin);
      
          if(user == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(userEntityDtoConverter.UserEntityToGetUserEntityDto(user));
        }
    }


    @Operation(summary = "Registra un nuevo gestor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han registrado un gestor.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha registrado ningún gestor.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})
            ,
            @ApiResponse(responseCode = "401",
                    description = "No tiene permiso para realizar esta acción.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})})
    @PostMapping("/register/gestor")
    public ResponseEntity<GetUserEntityDto> nuevoGestor(@RequestBody CreateUserEntityDto nuevoGestor){

        UserEntity user = userEntityService.saveGestor(nuevoGestor);


        if(user == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(userEntityDtoConverter.UserEntityToGetUserEntityDto(user));
        }
    }


}
