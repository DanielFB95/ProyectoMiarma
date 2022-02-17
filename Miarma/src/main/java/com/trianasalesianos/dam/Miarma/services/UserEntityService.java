package com.trianasalesianos.dam.Miarma.services;

import com.trianasalesianos.dam.Miarma.exceptions.NotPublicProfileException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final FileSystemStorageService storageService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByNick(username).orElseThrow(()-> new UsernameNotFoundException(username + " no encontrado"));
    }

    public UserEntity register(CreateUserEntityDto createUserEntityDto, MultipartFile file){

        if(createUserEntityDto.getPassword().contentEquals(createUserEntityDto.getPassword2())){

            String filename = storageService.store(file);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/avatar/")
                .path(filename)
                .toUriString();

            /* Poner en el FyleSystemStorageService
            byte[] byteImg = Files.readAllBytes(Paths.get(filename));
            BufferedImage original = ImageIO.read(new ByteArrayInputStream(byteImg));
            BufferedImage scaled = Scalr.resize(original,128);

            ImageIO.write(scaled, "png", Files.newOutputStream(storageService.load(filename)));
            */

            UserEntity newUser = UserEntity.builder()
                    .nick(createUserEntityDto.getNick())
                    .email(createUserEntityDto.getEmail())
                    .password(passwordEncoder.encode(createUserEntityDto.getPassword()))
                    .fechaNacimiento(createUserEntityDto.getFechaNacimiento())
                    .publicProfile(createUserEntityDto.isPublicProfile())
                    .urlAvatar(uri)
                    .build();

            return save(newUser);

        }else{
            return null;
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
}
