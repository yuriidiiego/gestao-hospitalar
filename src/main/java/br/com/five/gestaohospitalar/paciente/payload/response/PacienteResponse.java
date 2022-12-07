package br.com.five.gestaohospitalar.paciente.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class PacienteResponse {
  private Long id;
  private String nome;
  private String cpf;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataNascimento;

  private String sexo;

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

  public LocalDate getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(LocalDate dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }
}
