package br.com.five.gestaohospitalar.usuario;

import br.com.five.gestaohospitalar.enums.TipoPerfil;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "perfis")
public class Perfil {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  private TipoPerfil nome;

  public Perfil() {}

  public Perfil(TipoPerfil nome) {
    this.nome = nome;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public TipoPerfil getNome() {
    return nome;
  }

  public void setNome(TipoPerfil nome) {
    this.nome = nome;
  }
}
