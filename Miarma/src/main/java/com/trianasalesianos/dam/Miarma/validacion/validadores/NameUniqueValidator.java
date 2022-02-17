package com.trianasalesianos.dam.Miarma.validacion.validadores;

import com.trianasalesianos.dam.Miarma.repositories.UserEntityRepository;
import com.trianasalesianos.dam.Miarma.validacion.anotaciones.NameUnique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameUniqueValidator implements ConstraintValidator<NameUnique, String> {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public void initialize(NameUnique constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return StringUtils.hasText(name) && !userEntityRepository.existsByEmail(name);
    }
}
