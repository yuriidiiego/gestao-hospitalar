package br.com.five.gestaohospitalar.usuario.payload.response;

import java.util.List;

public class UsuariorInfoResponse {
  private Long id;
  private String username;
  private String email;
  private List<String> roles;
  private String valueCookie;

  public UsuariorInfoResponse(
    Long id,
    String username,
    String email,
    List<String> roles
  ) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }

  public UsuariorInfoResponse(
    Long id,
    String username,
    String email,
    String valueCookie,
    List<String> roles
  ) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.valueCookie = valueCookie;
    this.roles = roles;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }

  public String getValueCookie() {
    return valueCookie;
  }

  public void setValueCookie(String valueCookie) {
    this.valueCookie = valueCookie;
  }
}
