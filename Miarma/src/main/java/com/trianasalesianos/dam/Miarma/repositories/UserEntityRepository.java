package com.trianasalesianos.dam.Miarma.repositories;

import com.trianasalesianos.dam.Miarma.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByNickContains(String nick);

    Optional<UserEntity> findByEmailContains(String email);

    boolean existsByEmail(String name);
}
