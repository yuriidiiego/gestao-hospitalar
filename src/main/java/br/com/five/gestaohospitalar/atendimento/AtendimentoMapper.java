package br.com.five.gestaohospitalar.atendimento;

import br.com.five.gestaohospitalar.atendimento.payload.request.AtendimentoPostRequest;
import br.com.five.gestaohospitalar.atendimento.payload.request.AtendimentoPutRequest;
import br.com.five.gestaohospitalar.atendimento.payload.response.AtendimentoResponse;
import br.com.five.gestaohospitalar.medico.Medico;
import br.com.five.gestaohospitalar.paciente.Paciente;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AtendimentoMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "statusAtendimento", ignore = true)
  Atendimento toAtendimento(
    AtendimentoPostRequest atendimentoPostRequest,
    Medico medico,
    Paciente paciente
  );

  @Mapping(target = "nomeMedico", source = "medico.nome")
  @Mapping(target = "nomePaciente", source = "paciente.nome")
  AtendimentoResponse toAtendimentoResponse(Atendimento atendimento);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "statusAtendimento", ignore = true)
  @Mapping(target = "medico", ignore = true)
  @Mapping(target = "paciente", ignore = true)
  Atendimento toAtendimento(
    AtendimentoPutRequest atendimentoPutRequest,
    @MappingTarget Atendimento atendimento
  );

  List<AtendimentoResponse> toAtendimentoResponse(
    List<Atendimento> atendimentos
  );
}
