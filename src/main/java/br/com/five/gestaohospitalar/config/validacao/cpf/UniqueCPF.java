package br.com.five.gestaohospitalar.config.validacao.cpf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Constraint(validatedBy = { UniqueCPFValidator.class })
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCPF {
  String message() default "CPF com numero ${validatedValue} já cadastrado no sistema";

  Class<?>[] groups() default {};

  Class<?>[] payload() default {};
}
