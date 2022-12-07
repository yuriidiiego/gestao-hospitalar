package br.com.five.gestaohospitalar.usuario.payload.request;

import br.com.five.gestaohospitalar.config.validacao.email.UniqueEmail;
import br.com.five.gestaohospitalar.config.validacao.usuario.UniqueNomeUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.lang.Nullable;

public class UsuarioCadastroRequest {
  @Schema(
    name = "nomeUsuario",
    description = "Nome de usuário",
    example = "admin",
    required = true
  )
  @NotBlank(message = "{usuario.nomeUsuario.notBlank}")
  @Size(min = 3, max = 20, message = "{usuario.nomeUsuario.size}")
  @UniqueNomeUsuario
  private String nomeUsuario;

  @Schema(
    name = "email",
    description = "Email do usuário",
    example = "admin@example.com",
    required = true
  )
  @NotBlank(message = "{usuario.email.notBlank}")
  @Email(message = "{usuario.email.invalid}")
  @UniqueEmail
  private String email;

  @Nullable
  @Schema(
    name = "perfil",
    description = "Permissões do usuário",
    example = "[\"admin\"]",
    required = false
  )
  private Set<String> perfil;

  @Schema(
    name = "password",
    description = "Senha do usuário",
    example = "admin123",
    required = true
  )
  @NotBlank(message = "{usuario.senha.notBlank}")
  @Size(min = 6, max = 20, message = "{usuario.senha.size}")
  private String senha;

  public String getNomeUsuario() {
    return nomeUsuario;
  }

  public void setNomeUsuario(String nomeUsuario) {
    this.nomeUsuario = nomeUsuario;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<String> getPerfil() {
    return perfil;
  }

  public void setPerfil(Set<String> perfil) {
    this.perfil = perfil;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }
}
