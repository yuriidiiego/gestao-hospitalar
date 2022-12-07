package br.com.five.gestaohospitalar.config.validacao.email;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Constraint(validatedBy = { UniqueEmailValidator.class })
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
  String message() default "Email ${validatedValue} já cadastrado no sistema";

  Class<?>[] groups() default {};

  Class<?>[] payload() default {};
}
