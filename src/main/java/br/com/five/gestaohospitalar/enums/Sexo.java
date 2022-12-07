package br.com.five.gestaohospitalar.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sexo {
  MASCULINO,
  FEMININO;

  @JsonCreator
  public static Sexo fromString(String value) {
    return Sexo.valueOf(value.toUpperCase());
  }
}
