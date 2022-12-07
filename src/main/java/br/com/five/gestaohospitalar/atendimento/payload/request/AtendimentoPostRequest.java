package br.com.five.gestaohospitalar.atendimento.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

public class AtendimentoPostRequest {
  @Schema(
    description = "Data do atendimento",
    type = "string",
    example = "02/02/2022"
  )
  @Future(message = "{atendimento.dataAtendimento.future}")
  @NotNull(message = "{atendimento.dataAtendimento.notNull}")
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataAtendimento;

  @Schema(description = "Id do médico", example = "1")
  @NotNull(message = "{atendimento.idMedico.notNull}")
  private Long idMedico;

  @NotNull(message = "{atendimento.idPaciente.notNull}")
  @Schema(description = "Id do paciente", example = "1")
  private Long idPaciente;


  @Schema(
    description = "Observação do atendimento",
    example = "Paciente com dor de cabeça"
  )
  private String observacao;

  public LocalDate getDataAtendimento() {
    return dataAtendimento;
  }

  public void setDataAtendimento(LocalDate dataAtendimento) {
    this.dataAtendimento = dataAtendimento;
  }

  public Long getIdMedico() {
    return idMedico;
  }

  public void setIdMedico(Long idMedico) {
    this.idMedico = idMedico;
  }

  public Long getIdPaciente() {
    return idPaciente;
  }

  public void setIdPaciente(Long idPaciente) {
    this.idPaciente = idPaciente;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }
}
