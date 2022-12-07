package br.com.five.gestaohospitalar.atendimento;

import br.com.five.gestaohospitalar.enums.Status;
import br.com.five.gestaohospitalar.medico.Medico;
import br.com.five.gestaohospitalar.paciente.Paciente;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "atendimentos")
public class Atendimento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate dataAtendimento;

  @ManyToOne
  private Medico medico;

  @ManyToOne
  private Paciente paciente;

  private String observacao;

  @Enumerated(EnumType.STRING)
  private Status statusAtendimento;

  public Atendimento(
    LocalDate dataAtendimento,
    Medico medico,
    String observacao,
    Paciente paciente
  ) {
    this.dataAtendimento = dataAtendimento;
    this.medico = medico;
    this.observacao = observacao;
    this.paciente = paciente;
    this.statusAtendimento = Status.ATIVO;
  }

  public Atendimento() {
    this.statusAtendimento = Status.ATIVO;
  }

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

  public Medico getMedico() {
    return medico;
  }

  public void setMedico(Medico medico) {
    this.medico = medico;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }

  public Paciente getPaciente() {
    return paciente;
  }

  public void setPaciente(Paciente paciente) {
    this.paciente = paciente;
  }

  public Status getStatusAtendimento() {
    return statusAtendimento;
  }

  public void setStatusAtendimento(Status statusAtendimento) {
    this.statusAtendimento = statusAtendimento;
  }
}
