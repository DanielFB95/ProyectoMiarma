package com.trianasalesianos.dam.Miarma.services;


import com.trianasalesianos.dam.Miarma.errors.exceptions.PostNotFoundException;
import com.trianasalesianos.dam.Miarma.models.Publicacion;
import com.trianasalesianos.dam.Miarma.models.UserEntity;
import com.trianasalesianos.dam.Miarma.models.dto.ConvertersDto.PublicacionDtoConverter;
import com.trianasalesianos.dam.Miarma.models.dto.CreatesDto.CreatePublicacionDto;
import com.trianasalesianos.dam.Miarma.repositories.PublicacionRepository;
import com.trianasalesianos.dam.Miarma.repositories.UserEntityRepository;
import com.trianasalesianos.dam.Miarma.security.dto.JwtUserDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final FileSystemStorageService storageService;
    private final UserEntityService userEntityService;
    private final PublicacionDtoConverter publicacionDtoConverter;
    private final JwtUserDtoConverter jwtUserDtoConverter;

    public Publicacion save(CreatePublicacionDto createPublicacionDto, MultipartFile file) throws IOException {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/post")
                .path(filename)
                .toUriString();

        BufferedImage scaled = storageService.scale(filename);
        File outputfile = new File("uploads/1028_"+filename);
        ImageIO.write(scaled, "png", outputfile);
        String filenameScaled = StringUtils.cleanPath(outputfile.getName());

        String uriScaled = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/post")
                .path(filenameScaled)
                .toUriString();

        UserEntity user = userEntityService.findByEmailContains(createPublicacionDto.getUserEmail());

        user.addPublicacion(publicacionDtoConverter.createPublicacionDtoToPublicacion(createPublicacionDto));

            return Publicacion.builder()
                    .titulo(createPublicacionDto.getTitulo())
                    .texto(createPublicacionDto.getTexto())
                    .publicPost(createPublicacionDto.isPublicPost())
                    .url(uri)
                    .urlEscalada(uriScaled)
                    .build();
    }

    public Publicacion modificar(Long publicacionId, CreatePublicacionDto publicacionModificado, MultipartFile file) throws Exception {

        Publicacion publicacion = publicacionRepository.findById(publicacionId).orElseThrow(() -> new UserPrincipalNotFoundException("Usuario no encontrado"));

        storageService.deleteFile(publicacion.getUrl());
        storageService.deleteFile(publicacion.getUrlEscalada());

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/post")
                .path(filename)
                .toUriString();

        BufferedImage scaled = storageService.scale(filename);
        File outputfile = new File("uploads/1028_"+filename);
        ImageIO.write(scaled, "png", outputfile);
        String filenameScaled = StringUtils.cleanPath(outputfile.getName());

        String uriScaled = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/post")
                .path(filenameScaled)
                .toUriString();

        publicacion = Publicacion.builder()
                .texto(publicacionModificado.getTexto())
                .titulo(publicacionModificado.getTitulo())
                .publicPost(publicacion.isPublicPost())
                .url(uri)
                .urlEscalada(uriScaled)
                .build();

        publicacionRepository.save(publicacion);

        return publicacion;
    }

    public void delete(Long id){

        Publicacion p = publicacionRepository.findById(id).orElseThrow(() ->
                new PostNotFoundException("No se ha encontrado la publicación"));
        storageService.deleteFile(p.getUrl());
        storageService.deleteFile(p.getUrlEscalada());
        publicacionRepository.delete(p);
    }

    public List<Publicacion> publicPost(){
        return publicacionRepository.findByPublicPostTrue();
    }

    public Publicacion getOneById(Long id, UserEntity userEntity){
        Publicacion p = publicacionRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Publicación no encontrada"));
        UserEntity user = publicacionRepository.findById(id)
                .orElseThrow(()->new PostNotFoundException("No se ha encontrado la publicación")).getUsuario();
        if (p.isPublicPost() || userEntity.getListaSeguidores().contains(user)){
            return p;
        }
         return null;
    }

    public List<Publicacion> getAllPostByNick(String nick, UserEntity userEntity){

        UserEntity user = userEntityService.findByNick(nick);

        if(userEntity.getListaSeguidores().contains(user)){
            return user.getListaPublicaciones();
        }else{
            return user.getListaPublicaciones().stream()
                    .filter(p -> p.isPublicPost() == true)
                    .collect(Collectors.toList());
        }

    }

    public List<Publicacion> getAllPostOfUser(UserEntity userEntity){

        return userEntity.getListaPublicaciones();

    }

}

