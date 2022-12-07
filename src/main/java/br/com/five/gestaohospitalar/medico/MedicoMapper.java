package br.com.five.gestaohospitalar.medico;

import br.com.five.gestaohospitalar.medico.payload.request.MedicoPatchRequest;
import br.com.five.gestaohospitalar.medico.payload.request.MedicoPostRequest;
import br.com.five.gestaohospitalar.medico.payload.request.MedicoPutRequest;
import br.com.five.gestaohospitalar.medico.payload.response.MedicoResponse;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MedicoMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "atendimentos", ignore = true)
  Medico toMedico(MedicoPostRequest medicoPostRequest);

  MedicoResponse toMedicoResponse(Medico medico);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "atendimentos", ignore = true)
  Medico toMedico(MedicoPutRequest medicoPutRequest);

  @BeanMapping(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
  )
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "atendimentos", ignore = true)
  Medico toMedico(
    MedicoPatchRequest medicoPatchRequest,
    @MappingTarget Medico medico
  );

  List<MedicoResponse> toMedicoResponseList(List<Medico> medicos);

  @BeanMapping(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
  )
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "atendimentos", ignore = true)
  Medico toMedico(
    MedicoPutRequest medicoPutRequest,
    @MappingTarget Medico medico
  );
}
