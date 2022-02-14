package com.trianasalesianos.dam.Miarma.models;

import com.trianasalesianos.dam.Miarma.models.FileResponse;
import com.trianasalesianos.dam.Miarma.models.Publicaciones;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nick;
    private String email;
    private String password;
    private LocalDateTime fechaNacimiento;
    private boolean publicProfile;
    private String avatar;

    @Builder.Default
    @OneToMany(mappedBy = "usuario", cascade = {CascadeType.REMOVE})
    private List<Publicaciones> listaPublicaciones = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
