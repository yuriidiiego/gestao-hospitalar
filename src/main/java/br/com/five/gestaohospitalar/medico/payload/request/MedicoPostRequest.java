package br.com.five.gestaohospitalar.medico.payload.request;

import br.com.five.gestaohospitalar.config.validacao.cpf.UniqueCPF;
import br.com.five.gestaohospitalar.config.validacao.crm.CRM;
import br.com.five.gestaohospitalar.config.validacao.crm.UniqueCRM;
import br.com.five.gestaohospitalar.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

public class MedicoPostRequest {
  @Schema(description = "Nome do médico", example = "Doutor House")
  @NotBlank(message = "{medico.nome.notBlank}")
  private String nome;

  @Schema(description = "CPF do médico", example = "70989098230")
  @NotBlank(message = "{medico.cpf.notBlank}")
  @UniqueCPF
  @CPF
  private String cpf;

  @Schema(
    description = "Data de nascimento do médico",
    type = "string",
    example = "02/02/1980",
    pattern = "dd/MM/yyyy"
  )
  @NotNull(message = "{medico.dataNascimento.notNull}")
  @Past(message = "{medico.dataNascimento.past}")
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataNascimento;

  @Schema(
    description = "CRM do médico",
    example = "123456/SP",
    pattern = "^[\\d]{6}\\/[A-Z]{2}$"
  )
  @NotBlank(message = "{medico.crm.notBlank}")
  @UniqueCRM
  @CRM
  private String crm;

  @Schema(description = "Sexo do médico", example = "MASCULINO")
  @NotNull(message = "{medico.sexo.notNull}")
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

  public String getCrm() {
    return crm;
  }

  public void setCrm(String crm) {
    this.crm = crm;
  }

  public Sexo getSexo() {
    return sexo;
  }

  public void setSexo(Sexo sexo) {
    this.sexo = sexo;
  }
}
