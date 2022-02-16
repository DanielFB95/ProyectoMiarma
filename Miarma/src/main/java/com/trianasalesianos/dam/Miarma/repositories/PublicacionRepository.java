package com.trianasalesianos.dam.Miarma.repositories;

import com.trianasalesianos.dam.Miarma.models.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion,Long> {

    List<Publicacion> findByPublicPostTrue();
}
