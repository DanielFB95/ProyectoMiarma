package com.trianasalesianos.dam.Miarma.repositories;

import com.trianasalesianos.dam.Miarma.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
}
