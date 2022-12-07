package br.com.five.gestaohospitalar.medico.payload.response;

import br.com.five.gestaohospitalar.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class MedicoResponse {
  private Long id;
  private String nome;

  private String cpf;

  private String crm;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataNascimento;

  private Sexo sexo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public String getCrm() {
    return crm;
  }

  public void setCrm(String crm) {
    this.crm = crm;
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
