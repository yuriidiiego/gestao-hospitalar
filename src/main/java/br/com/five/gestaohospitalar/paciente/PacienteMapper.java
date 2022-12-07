package br.com.five.gestaohospitalar.paciente;

import br.com.five.gestaohospitalar.paciente.payload.request.PacientePostRequest;
import br.com.five.gestaohospitalar.paciente.payload.request.PacientePutRequest;
import br.com.five.gestaohospitalar.paciente.payload.response.PacienteResponse;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "atendimentos", ignore = true)
  Paciente toPaciente(PacientePostRequest pacientePostRequest);

  PacienteResponse toPacienteResponse(Paciente paciente);

  List<PacienteResponse> toPacienteResponseList(List<Paciente> pacientes);

  @BeanMapping(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
  )
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "atendimentos", ignore = true)
  Paciente toPaciente(
    PacientePostRequest pacientPostRequest,
    @MappingTarget Paciente paciente
  );

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "atendimentos", ignore = true)
  Paciente toPaciente(
    PacientePutRequest pacientePutRequest,
    @MappingTarget Paciente paciente
  );
}
