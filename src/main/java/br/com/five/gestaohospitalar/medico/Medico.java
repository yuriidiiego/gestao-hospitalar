package br.com.five.gestaohospitalar.medico;

import br.com.five.gestaohospitalar.atendimento.Atendimento;
import br.com.five.gestaohospitalar.dadopessoal.DadoPessoal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "medicos")
public class Medico extends DadoPessoal {
  private String crm;

  @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY)
  private List<Atendimento> atendimentos = new ArrayList<>();

  public String getCrm() {
    return crm;
  }

  public void setCrm(String crm) {
    this.crm = crm;
  }

  public List<Atendimento> getAtendimentos() {
    return atendimentos;
  }

  public void setAtendimentos(List<Atendimento> atendimentos) {
    this.atendimentos = atendimentos;
  }
}
