package br.com.five.gestaohospitalar.config.validacao.crm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.five.gestaohospitalar.medico.MedicoRepository;

public class UniqueCRMValidator
  implements ConstraintValidator<UniqueCRM, String> {
  @Autowired
  private MedicoRepository medicoRepository;

  @Override
  public boolean isValid(String crm, ConstraintValidatorContext context) {
    return !medicoRepository.existsByCrm(crm);
  }
}
