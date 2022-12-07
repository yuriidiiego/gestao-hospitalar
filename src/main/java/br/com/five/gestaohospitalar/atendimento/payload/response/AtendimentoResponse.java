package br.com.five.gestaohospitalar.atendimento.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class AtendimentoResponse {
  private Long id;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataAtendimento;

  private String nomeMedico;
  private String nomePaciente;
  private String observacao;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDataAtendimento() {
    return dataAtendimento;
  }

  public void setDataAtendimento(LocalDate dataAtendimento) {
    this.dataAtendimento = dataAtendimento;
  }

  public String getNomeMedico() {
    return nomeMedico;
  }

  public void setNomeMedico(String nomeMedico) {
    this.nomeMedico = nomeMedico;
  }

  public String getNomePaciente() {
    return nomePaciente;
  }

  public void setNomePaciente(String nomePaciente) {
    this.nomePaciente = nomePaciente;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }
}
