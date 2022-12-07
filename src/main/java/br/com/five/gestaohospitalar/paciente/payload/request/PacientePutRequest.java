package br.com.five.gestaohospitalar.paciente.payload.request;

import br.com.five.gestaohospitalar.config.validacao.cpf.UniqueCPF;
import br.com.five.gestaohospitalar.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

public class PacientePutRequest {
  private String nome;

  @CPF
  @UniqueCPF
  private String cpf;

  @Past(message = "{paciente.dataNascimento.past}")
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataNascimento;

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
