package com.trianasalesianos.dam.Miarma.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
public class FileResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String uri;
    private String type;
    private long size;
}
