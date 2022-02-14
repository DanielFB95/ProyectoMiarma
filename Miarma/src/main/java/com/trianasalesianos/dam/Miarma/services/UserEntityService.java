package com.trianasalesianos.dam.Miarma.services;

import com.trianasalesianos.dam.Miarma.services.base.BaseService;
import com.trianasalesianos.dam.Miarma.models.UserEntity;
import com.trianasalesianos.dam.Miarma.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity,Long, UserEntityRepository> {

}
