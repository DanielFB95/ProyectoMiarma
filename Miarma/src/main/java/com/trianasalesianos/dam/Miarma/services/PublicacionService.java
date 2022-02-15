package com.trianasalesianos.dam.Miarma.services;

import com.trianasalesianos.dam.Miarma.models.Publicacion;
import com.trianasalesianos.dam.Miarma.models.UserEntity;
import com.trianasalesianos.dam.Miarma.models.dto.ConvertersDto.PublicacionDtoConverter;
import com.trianasalesianos.dam.Miarma.models.dto.CreatesDto.CreatePublicacionDto;
import com.trianasalesianos.dam.Miarma.repositories.PublicacionRepository;
import com.trianasalesianos.dam.Miarma.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service
@RequiredArgsConstructor
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final FileSystemStorageService storageService;
    private final UserEntityRepository userEntityRepository;
    private final PublicacionDtoConverter publicacionDtoConverter;

    public Publicacion save(CreatePublicacionDto createPublicacionDto, MultipartFile file) throws IOException {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/post")
                .path(filename)
                .toUriString();

        byte[] byteImg = Files.readAllBytes(Paths.get(filename));
        BufferedImage original = ImageIO.read(new ByteArrayInputStream(byteImg));
        BufferedImage scaled = Scalr.resize(original,1024);
        String scaledFilename = storageService.store((MultipartFile) scaled);
        String uriScaled = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/post")
                .path(scaledFilename)
                .toUriString();

        UserEntity user = userEntityRepository.findByEmail(createPublicacionDto.getUserEmail()).orElseThrow(() ->
                new UserPrincipalNotFoundException("No se ha encontrado el email: "+createPublicacionDto.getUserEmail()));

        user.addPublicacion(publicacionDtoConverter.createPublicacionDtoToPublicacion(createPublicacionDto));

            return Publicacion.builder()
                    .titulo(createPublicacionDto.getTitulo())
                    .texto(createPublicacionDto.getTexto())
                    .publicPost(createPublicacionDto.isPublicPost())
                    .url(uri)
                    .urlEscalada(uriScaled)
                    .build();
    }

}

