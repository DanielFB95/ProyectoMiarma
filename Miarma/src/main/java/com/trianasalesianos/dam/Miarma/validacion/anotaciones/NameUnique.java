package com.trianasalesianos.dam.Miarma.validacion.anotaciones;

import com.trianasalesianos.dam.Miarma.validacion.validadores.NameUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = NameUniqueValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Documented
public @interface NameUnique {
    String message() default "{name.unique}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
