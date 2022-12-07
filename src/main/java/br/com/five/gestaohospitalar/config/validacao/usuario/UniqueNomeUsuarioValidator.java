package br.com.five.gestaohospitalar.config.validacao.usuario;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.five.gestaohospitalar.usuario.UsuarioRepository;

public class UniqueNomeUsuarioValidator
  implements ConstraintValidator<UniqueNomeUsuario, String> {
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public boolean isValid(String nomeUsuario, ConstraintValidatorContext context) {
    return !usuarioRepository.existsByNomeUsuario(nomeUsuario);
  }
}
