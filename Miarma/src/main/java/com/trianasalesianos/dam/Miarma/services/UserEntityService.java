package com.trianasalesianos.dam.Miarma.services;

import com.trianasalesianos.dam.Miarma.errors.exceptions.EmailNotFoundException;
import com.trianasalesianos.dam.Miarma.errors.exceptions.NotPublicProfileException;
import com.trianasalesianos.dam.Miarma.errors.exceptions.RegisterException;
import com.trianasalesianos.dam.Miarma.models.dto.CreatesDto.CreateUserEntityDto;
import com.trianasalesianos.dam.Miarma.services.base.BaseService;
import com.trianasalesianos.dam.Miarma.models.UserEntity;
import com.trianasalesianos.dam.Miarma.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final FileSystemStorageService storageService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByEmailContains(username).orElseThrow(()-> new UsernameNotFoundException(username + " no encontrado"));
    }

    public UserEntity register(CreateUserEntityDto createUserEntityDto, MultipartFile file) throws IOException {

        if(createUserEntityDto.getPassword().contentEquals(createUserEntityDto.getPassword2())){

            String filename = storageService.store(file);

            BufferedImage scaled = storageService.scale(filename);
            File outputfile = new File("uploads/1028_"+filename);
            ImageIO.write(scaled, "png", outputfile);
            String filenameScaled = StringUtils.cleanPath(outputfile.getName());

            String uriScaled = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/post")
                    .path(filenameScaled)
                    .toUriString();

            UserEntity newUser = UserEntity.builder()
                    .nick(createUserEntityDto.getNick())
                    .email(createUserEntityDto.getEmail())
                    .password(passwordEncoder.encode(createUserEntityDto.getPassword()))
                    .fechaNacimiento(createUserEntityDto.getFechaNacimiento())
                    .publicProfile(createUserEntityDto.isPublicProfile())
                    .urlAvatar(uriScaled)
                    .build();

            return save(newUser);

        }else{
            throw new RegisterException("No se ha podido generar el usuario.");
        }
    }

    public UserEntity findOne(UUID uuid, UserEntity userEntity){

        boolean publicProfile = repository.getById(uuid).isPublicProfile();
        if(userEntity.getListaSeguidores().contains(repository.getById(uuid)) || publicProfile ){
            return repository.getById(uuid);
        }else{
            throw new NotPublicProfileException("El usuario que está intentado acceder no es público y no lo sigues.");
        }

    }

    public UserEntity modificarUsuario(UserEntity userEntity,CreateUserEntityDto createUserEntityDto,MultipartFile file){

        UserEntity user = repository.getById(userEntity.getId());

        user.setEmail(createUserEntityDto.getEmail());
        user.setFechaNacimiento(createUserEntityDto.getFechaNacimiento());
        user.setNick(createUserEntityDto.getNick());
        user.setPassword(createUserEntityDto.getPassword());
        user.setPublicProfile(createUserEntityDto.isPublicProfile());

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/avatar/")
                .path(filename)
                .toUriString();

        user.setUrlAvatar(uri);
        return user;
    }

    public UserEntity findByEmailContains(String email){
        return repository.findByEmailContains(email).orElseThrow(()-> new EmailNotFoundException("No se ha encontrado el email"));
    }

    public UserEntity findByNick(String nick){
        return repository.findByNick(nick).orElseThrow(()-> new UsernameNotFoundException("No se ha encontrado el nick: "+nick));
    }
}
