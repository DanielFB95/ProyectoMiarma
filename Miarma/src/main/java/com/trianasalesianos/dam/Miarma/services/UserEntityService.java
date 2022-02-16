package com.trianasalesianos.dam.Miarma.services;

import com.trianasalesianos.dam.Miarma.models.dto.CreatesDto.CreateUserEntityDto;
import com.trianasalesianos.dam.Miarma.services.base.BaseService;
import com.trianasalesianos.dam.Miarma.models.UserEntity;
import com.trianasalesianos.dam.Miarma.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final FileSystemStorageService storageService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByNick(username).orElseThrow(()-> new UsernameNotFoundException(username + " no encontrado"));
    }

    public UserEntity register(CreateUserEntityDto createUserEntityDto, MultipartFile file) throws IOException {

        if(createUserEntityDto.getPassword().contentEquals(createUserEntityDto.getPassword2())){

            String filename = storageService.store(file);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/avatar/")
                .path(filename)
                .toUriString();

        byte[] byteImg = Files.readAllBytes(Paths.get(filename));
        BufferedImage original = ImageIO.read(new ByteArrayInputStream(byteImg));
        BufferedImage scaled = Scalr.resize(original,128);

            UserEntity newUser = UserEntity.builder()
                    .nick(createUserEntityDto.getNick())
                    .email(createUserEntityDto.getEmail())
                    .password(createUserEntityDto.getPassword())
                    .fechaNacimiento(createUserEntityDto.getFechaNacimiento())
                    .publicProfile(createUserEntityDto.isPublicProfile())
                    .urlAvatar(uri)
                    .build();

            return save(newUser);

        }else{
            return null;
        }
    }
}
