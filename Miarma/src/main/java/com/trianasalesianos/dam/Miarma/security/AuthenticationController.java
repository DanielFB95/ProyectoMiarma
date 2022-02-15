package com.trianasalesianos.dam.Miarma.security;


import com.trianasalesianos.dam.Miarma.models.UserEntity;
import com.trianasalesianos.dam.Miarma.models.dto.ConvertersDto.UserEntityDtoConverter;
import com.trianasalesianos.dam.Miarma.models.dto.CreatesDto.CreateUserEntityDto;
import com.trianasalesianos.dam.Miarma.models.dto.GetsDto.GetUserEntityDto;
import com.trianasalesianos.dam.Miarma.security.dto.JwtUserDtoConverter;
import com.trianasalesianos.dam.Miarma.security.dto.LoginDto;
import com.trianasalesianos.dam.Miarma.security.jwt.JwtProvider;
import com.trianasalesianos.dam.Miarma.services.UserEntityService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Controller authentication")
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
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "401",
                    description = "No tiene permiso para realizar esta acción.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})})
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
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "401",
                    description = "No tiene permiso para realizar esta acción.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})})
    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserEntity user){
        return ResponseEntity.ok(jwtUserDtoConverter.userToJwtUserResponse(user,null));
    }

    @Operation(summary = "Registra el usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el usuario.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el usuario.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "401",
                    description = "No tiene permiso para realizar esta acción.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})})

    @PostMapping("/register")
    public ResponseEntity<GetUserEntityDto> register(@RequestPart("user")CreateUserEntityDto newUser, @RequestPart("file")MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userEntityDtoConverter.UserEntityToGetUserEntityDto(userEntityService.register(newUser, file)));
    }
}

