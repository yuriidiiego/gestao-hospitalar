package br.com.five.gestaohospitalar.config.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ErrorResponse {
  private int statuscode;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  private LocalDateTime timestamp;

  private String erro;

  public ErrorResponse(int statuscode, String erro) {
    this.statuscode = statuscode;
    this.timestamp = LocalDateTime.now();
    this.erro = erro;
  }

  public ErrorResponse(String erro) {
    this.timestamp = LocalDateTime.now();
    this.erro = erro;
  }

  public ErrorResponse() {}

  public int getStatuscode() {
    return statuscode;
  }

  public void setStatuscode(int statuscode) {
    this.statuscode = statuscode;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getErro() {
    return erro;
  }

  public void setErro(String erro) {
    this.erro = erro;
  }
}
