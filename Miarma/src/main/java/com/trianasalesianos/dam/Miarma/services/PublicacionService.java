package com.trianasalesianos.dam.Miarma.services;

import com.trianasalesianos.dam.Miarma.models.Publicacion;
import com.trianasalesianos.dam.Miarma.models.dto.CreatesDto.CreatePublicacionDto;
import com.trianasalesianos.dam.Miarma.repositories.PublicacionRepository;
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

@Service
@RequiredArgsConstructor
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final FileSystemStorageService storageService;

    public Publicacion save(CreatePublicacionDto createPublicacionDto, MultipartFile file) throws IOException {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/post")
                .path(filename)
                .toUriString();

        byte[] byteImg = Files.readAllBytes(Paths.get(filename));
        BufferedImage original = ImageIO.read(new ByteArrayInputStream(byteImg));
        BufferedImage scaled = Scalr.resize(original,1024);

        return Publicacion.builder()
                .titulo(createPublicacionDto.getTitulo())
                .texto(createPublicacionDto.getTexto())
                .publicPost(createPublicacionDto.isPublicPost())
                .url(uri)
                //.urlEscalada()
                .build();
    }
}
