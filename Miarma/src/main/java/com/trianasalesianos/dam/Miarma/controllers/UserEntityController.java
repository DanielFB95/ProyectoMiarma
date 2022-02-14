package com.trianasalesianos.dam.Miarma.controllers;

import com.trianasalesianos.dam.Miarma.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserEntityController {

    private UserEntityService userEntityService;

}
