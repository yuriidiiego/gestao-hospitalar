package br.com.five.gestaohospitalar.usuario.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;

public class UsuarioLoginRequest {
  @Schema(
    name = "nomeUsuario",
    description = "Nome de usuário",
    example = "admin",
    required = true
  )
  @NotBlank(message = "{usuario.nomeUsuario.notBlank}")
  private String nomeUsuario;

  @Schema(
    name = "senha",
    description = "Senha do usuário",
    example = "admin123",
    required = true
  )
  @NotBlank(message = "{usuario.senha.notBlank}")
  private String senha;

  public String getNomeUsuario() {
    return nomeUsuario;
  }

  public void setNomeUsuario(String nomeUsuario) {
    this.nomeUsuario = nomeUsuario;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }
}
