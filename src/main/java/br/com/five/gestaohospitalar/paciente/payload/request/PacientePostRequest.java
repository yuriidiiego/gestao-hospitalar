package br.com.five.gestaohospitalar.paciente.payload.request;

import br.com.five.gestaohospitalar.config.validacao.cpf.UniqueCPF;
import br.com.five.gestaohospitalar.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

public class PacientePostRequest {
  @Schema(description = "Nome do paciente", example = "João da Silva")
  @NotBlank(message = "{paciente.nome.notBlank}")
  private String nome;

  @Schema(description = "CPF do paciente", example = "70989098230")
  @NotBlank(message = "{paciente.cpf.notBlank}")
  @UniqueCPF
  @CPF
  private String cpf;

  @Schema(
    description = "Data de nascimento do paciente",
    type = "string",
    example = "02/02/1980",
    pattern = "dd/MM/yyyy"
  )
  @NotNull(message = "{paciente.dataNascimento.notNull}")
  @Past(message = "{paciente.dataNascimento.past}")
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataNascimento;

  @NotNull(message = "{paciente.sexo.notNull}")
  @Schema(description = "Sexo do paciente", example = "MASCULINO")
  private Sexo sexo;

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public LocalDate getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(LocalDate dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public Sexo getSexo() {
    return sexo;
  }

  public void setSexo(Sexo sexo) {
    this.sexo = sexo;
  }
}
