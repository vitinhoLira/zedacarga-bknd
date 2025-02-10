package br.com.zedacarga.zedacarga_api.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CNHValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CNH {
    String message() default "CNH inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
