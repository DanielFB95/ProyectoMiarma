package com.trianasalesianos.dam.Miarma.controllers;

import com.trianasalesianos.dam.Miarma.services.UserEntityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Tag(name = "User", description = "Controller de usuario")
public class UserEntityController {

    private UserEntityService userEntityService;

}
