package br.com.five.gestaohospitalar.config.validacao.email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.five.gestaohospitalar.usuario.UsuarioRepository;

public class UniqueEmailValidator
  implements ConstraintValidator<UniqueEmail, String> {
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return !usuarioRepository.existsByEmail(email);
  }
}
