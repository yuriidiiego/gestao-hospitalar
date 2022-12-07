package br.com.five.gestaohospitalar.config.validacao.crm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Constraint(validatedBy = { CRMValidator.class })
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CRM {
  String message() default "CRM inválido! Exemplo de CRM válido: 123456/SP";

  Class<?>[] groups() default {};

  Class<?>[] payload() default {};
}
