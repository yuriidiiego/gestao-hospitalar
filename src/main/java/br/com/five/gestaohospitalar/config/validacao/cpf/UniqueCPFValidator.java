package br.com.five.gestaohospitalar.config.validacao.cpf;

import br.com.five.gestaohospitalar.medico.MedicoRepository;
import br.com.five.gestaohospitalar.paciente.PacienteRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueCPFValidator
  implements ConstraintValidator<UniqueCPF, String> {
  @Autowired
  private PacienteRepository pacienteRepository;

  @Autowired
  private MedicoRepository medicoRepository;

  @Override
  public boolean isValid(String cpf, ConstraintValidatorContext context) {
    return (
      !pacienteRepository.existsByCpf(cpf) && !medicoRepository.existsByCpf(cpf)
    );
  }
}
