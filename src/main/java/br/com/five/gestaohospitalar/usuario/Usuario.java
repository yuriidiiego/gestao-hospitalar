package br.com.five.gestaohospitalar.usuario;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
  name = "usuarios",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = "nomeUsuario"),
    @UniqueConstraint(columnNames = "email"),
  }
)
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nomeUsuario;

  private String email;

  private String senha;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "usuarios_perfis",
    joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "perfil_id")
  )
  private Set<Perfil> perfis = new HashSet<>();

  public Usuario() {}

  public Usuario(String nomeUsuario, String email, String senha) {
    this.nomeUsuario = nomeUsuario;
    this.email = email;
    this.senha = senha;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNomeUsuario() {
    return nomeUsuario;
  }

  public void setNomeUsuario(String nomeUsuario) {
    this.nomeUsuario = nomeUsuario;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<Perfil> getPerfis() {
    return perfis;
  }

  public void setPerfis(Set<Perfil> perfis) {
    this.perfis = perfis;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }
}
