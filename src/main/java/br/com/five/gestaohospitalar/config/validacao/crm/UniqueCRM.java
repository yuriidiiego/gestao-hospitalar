package br.com.five.gestaohospitalar.config.validacao.crm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Constraint(validatedBy = { UniqueCRMValidator.class })
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCRM {
  String message() default "CRM com numero ${validatedValue} já cadastrado no sistema";

  Class<?>[] groups() default {};

  Class<?>[] payload() default {};
}
