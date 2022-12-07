package br.com.five.gestaohospitalar.paciente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.five.gestaohospitalar.atendimento.Atendimento;
import br.com.five.gestaohospitalar.dadopessoal.DadoPessoal;

@Entity
@Table(name = "pacientes")
public class Paciente extends DadoPessoal {
  @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
  private List<Atendimento> atendimentos = new ArrayList<>();

  public List<Atendimento> getAtendimentos() {
    return atendimentos;
  }

  public void setAtendimentos(List<Atendimento> atendimentos) {
    this.atendimentos = atendimentos;
  }
}
