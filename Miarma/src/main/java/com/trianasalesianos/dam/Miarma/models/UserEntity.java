package com.trianasalesianos.dam.Miarma.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id",updatable = false,nullable = false)
    private UUID id;

    private String nick;
    private String email;
    private String password;
    private LocalDate fechaNacimiento;
    private boolean publicProfile;
    private String urlAvatar;

    @Builder.Default
    @OneToMany(mappedBy = "usuario", cascade = {CascadeType.REMOVE})
    private List<Publicacion> listaPublicaciones = new ArrayList<>();

    @ManyToMany
    private List<UserEntity> listaSeguidores;

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

    public void addPublicacion(Publicacion p){
        p.setUsuario(this);
        listaPublicaciones.add(p);
    }

    /*
    @OneToMany
    @Builder.Default
    List<UserEntity> listaSeguimiento = new ArrayList<>();

    //Opcional: hacer que no se repita la pareja de atributos (EmbeddedID, @ManyToMany bidireccional)
    Crear otra clase Follow{
        @ManyToOne
        UserEntity seguidores;

        @ManyToOne
        UserEntity seguidos;
    }
     */
}

