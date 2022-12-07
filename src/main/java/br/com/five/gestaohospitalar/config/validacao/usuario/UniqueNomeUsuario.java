package br.com.five.gestaohospitalar.config.validacao.usuario;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Constraint(validatedBy = { UniqueNomeUsuarioValidator.class })
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueNomeUsuario {
  String message() default "Nome de usuário ${validatedValue} já está sendo utilizado";

  Class<?>[] groups() default {};

  Class<?>[] payload() default {};
}
