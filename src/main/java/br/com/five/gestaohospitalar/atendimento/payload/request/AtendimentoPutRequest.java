package br.com.five.gestaohospitalar.atendimento.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class AtendimentoPutRequest {
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataAtendimento;

  private String observacao;

  public LocalDate getDataAtendimento() {
    return dataAtendimento;
  }

  public void setDataAtendimento(LocalDate dataAtendimento) {
    this.dataAtendimento = dataAtendimento;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }
}
