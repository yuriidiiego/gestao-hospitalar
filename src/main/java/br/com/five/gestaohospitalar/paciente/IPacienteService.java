package br.com.five.gestaohospitalar.paciente;

import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import br.com.five.gestaohospitalar.paciente.payload.request.PacientePatchRequest;
import br.com.five.gestaohospitalar.paciente.payload.request.PacientePostRequest;
import br.com.five.gestaohospitalar.paciente.payload.request.PacientePutRequest;
import br.com.five.gestaohospitalar.paciente.payload.response.PacienteResponse;

public interface IPacienteService {
  PacienteResponse salvar(PacientePostRequest pacientePostDTO);

  PacienteResponse buscarPorId(Long id);

  List<PacienteResponse> buscarTodos();

  void deletar(Long id);

  PacienteResponse atualizar(Long id, PacientePutRequest pacientePutDTO);

  PacienteResponse atualizarParcialmente(
    Long id,
    PacientePatchRequest pacientePatchDTO
  );

  List<PacienteResponse> listaPacientePorMedicoID(Long id);

  ResponseEntity<InputStreamResource> gerarPdfPacientes();
}
