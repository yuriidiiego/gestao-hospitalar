package br.com.five.gestaohospitalar.medico;

import br.com.five.gestaohospitalar.medico.payload.request.MedicoPatchRequest;
import br.com.five.gestaohospitalar.medico.payload.request.MedicoPostRequest;
import br.com.five.gestaohospitalar.medico.payload.request.MedicoPutRequest;
import br.com.five.gestaohospitalar.medico.payload.response.MedicoResponse;
import java.time.LocalDate;
import java.util.List;

public interface IMedicoService {
  MedicoResponse salvar(MedicoPostRequest medicoPostDTO);

  MedicoResponse buscarPorId(Long id);

  List<MedicoResponse> buscarTodos();

  void deletar(Long id);

  MedicoResponse atualizar(Long id, MedicoPutRequest medicoPutDTO);

  MedicoResponse atualizarParcialmente(
    Long id,
    MedicoPatchRequest medicoPatchDTO
  );

  List<MedicoResponse> listaMedicosPorPeriodo(
    LocalDate dataInicio,
    LocalDate dataFim
  );

  List<MedicoResponse> listaMedicosPorPacienteID(Long id);
}
