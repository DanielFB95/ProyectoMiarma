package com.trianasalesianos.dam.Miarma.models;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
public class Publicaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String texto;
    private boolean publicPost;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private UserEntity usuario;

}
