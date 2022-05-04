package com.mercadolibre.grupo1.projetointegrador.anotations.validation;

import com.mercadolibre.grupo1.projetointegrador.anotations.validation.impl.CPFValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CPFValidator.class)
public @interface CPF {
    String message() default "Inválid CPF";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
