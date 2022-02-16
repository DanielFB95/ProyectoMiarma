package com.trianasalesianos.dam.Miarma.models;

import lombok.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/*
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Follow implements Serializable {

    @Builder.Default
    @EmbeddedId
    private FollowPK id = new FollowPK();

    @ManyToOne
    @MapsId("usuario_seguido")
    @JoinColumn(name = "usuario_seguido")
    private UserEntity usuarioSeguido;

    @ManyToOne
    @MapsId("usuario_seguidor")
    @JoinColumn(name = "usuario_seguidor")
    private UserEntity usuarioSeguidor;

    public void addToUserEntity(UserEntity user){
        usuarioSeguido = user;
        user.getListaSeguidores().add(this.usuarioSeguido);
    }

    public void removeFromUserEntity(UserEntity user){
        user.getListaSeguidores().remove(this.usuarioSeguido);
        usuarioSeguido = null;
    }
}

 */
