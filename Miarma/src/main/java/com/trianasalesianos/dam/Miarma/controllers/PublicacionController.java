package com.trianasalesianos.dam.Miarma.controllers;

import com.trianasalesianos.dam.Miarma.models.Publicacion;
import com.trianasalesianos.dam.Miarma.models.UserEntity;
import com.trianasalesianos.dam.Miarma.models.dto.ConvertersDto.PublicacionDtoConverter;
import com.trianasalesianos.dam.Miarma.models.dto.CreatesDto.CreatePublicacionDto;
import com.trianasalesianos.dam.Miarma.models.dto.GetsDto.GetPublicacionDto;
import com.trianasalesianos.dam.Miarma.services.PublicacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Tag(name = "Publicacion", description = "Controller de publicación")
public class PublicacionController {

    private final PublicacionService publicacionService;
    private final PublicacionDtoConverter publicacionDtoConverter;

    @PostMapping("/")
    public ResponseEntity<GetPublicacionDto> save(@RequestPart("publicacion")CreatePublicacionDto nuevaPublicacion,
                                                  @RequestPart("file")MultipartFile file) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(publicacionDtoConverter.publicacionToGetPublicacionDto(publicacionService.save(nuevaPublicacion, file)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publicacion> edit(@PathVariable("id")Long id,@RequestPart("publicacion")CreatePublicacionDto publicacionEditada,
                                            @RequestPart("file")MultipartFile file) throws Exception {

        return ResponseEntity.ok().body(publicacionService.modificar(id,publicacionEditada,file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        publicacionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public")
    public ResponseEntity<List<GetPublicacionDto>> publicPost(){
        return ResponseEntity.ok()
                .body(publicacionService.publicPost().stream()
                        .map(publicacionDtoConverter::publicacionToGetPublicacionDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPublicacionDto> getOnePost(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity){
            return ResponseEntity.ok()
                    .body(publicacionDtoConverter
                            .publicacionToGetPublicacionDto(publicacionService.getOneById(id, userEntity)));

    }

    @GetMapping("/{nick}")
    public ResponseEntity<List<GetPublicacionDto>> getAllPostByNick(@PathVariable("nick") String nick,
                                                                    @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.ok().body(publicacionService.getAllPostByNick(nick,userEntity).stream()
                .map(publicacionDtoConverter::publicacionToGetPublicacionDto).collect(Collectors.toList()));
    }

}
