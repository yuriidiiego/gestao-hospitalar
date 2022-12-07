package br.com.five.gestaohospitalar.paciente;

import br.com.five.gestaohospitalar.atendimento.AtendimentoRepository;
import br.com.five.gestaohospitalar.medico.MedicoRepository;
import br.com.five.gestaohospitalar.paciente.payload.request.PacientePatchRequest;
import br.com.five.gestaohospitalar.paciente.payload.request.PacientePostRequest;
import br.com.five.gestaohospitalar.paciente.payload.request.PacientePutRequest;
import br.com.five.gestaohospitalar.paciente.payload.response.PacienteResponse;
import br.com.five.gestaohospitalar.util.GeradorPdfUtil;
import java.io.ByteArrayInputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PacienteServiceImpl implements IPacienteService {
  @Autowired
  private PacienteRepository pacienteRepository;

  @Autowired
  private MedicoRepository medicoRepository;

  @Autowired
  private AtendimentoRepository atendimentoRepository;

  @Autowired
  private PacienteMapper pacienteMapper;

  @Override
  public PacienteResponse salvar(PacientePostRequest pacientePostDTO) {
    return pacienteMapper.toPacienteResponse(
      pacienteRepository.save(pacienteMapper.toPaciente(pacientePostDTO))
    );
  }

  @Override
  public PacienteResponse buscarPorId(Long id) {
    return pacienteMapper.toPacienteResponse(
      buscaPacientePorIdOrElseThrow(id)
    );
  }

  @Override
  public List<PacienteResponse> buscarTodos() {
    return pacienteMapper.toPacienteResponseList(
      pacienteRepository.findAll()
    );
  }

  @Override
  public void deletar(Long id) {
    if (verificaSePacientePossuiAtendimento(id)) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Paciente não pode ser deletado pois possui atendimento vinculado"
      );
    }
    pacienteRepository.delete(buscaPacientePorIdOrElseThrow(id));
  }

  @Override
  public PacienteResponse atualizar(Long id, PacientePutRequest pacientePutReqeust) {
    Paciente paciente = buscaPacientePorIdOrElseThrow(id);
    pacienteMapper.toPaciente(pacientePutReqeust, paciente);
    return pacienteMapper.toPacienteResponse(
      pacienteRepository.save(paciente)
    );
  }

  @Override
  public PacienteResponse atualizarParcialmente(
    Long id,
    PacientePatchRequest pacientePatchDTO
  ) {
    Paciente paciente = buscaPacientePorIdOrElseThrow(id);
    pacienteMapper.toPaciente(pacientePatchDTO, paciente);
    return pacienteMapper.toPacienteResponse(
      pacienteRepository.save(paciente)
    );
  }

  @Override
  public List<PacienteResponse> listaPacientePorMedicoID(Long id) {
    List<Paciente> pacientes = buscaMedicoNoBancoOrElseThrow(id);
    return pacienteMapper.toPacienteResponseList(pacientes);
  }

  @Override
  public ResponseEntity<InputStreamResource> gerarPdfPacientes() {
    List<Paciente> pacientes = pacienteRepository.findAll();

    ByteArrayInputStream bis = GeradorPdfUtil.gerarPdfPacientes(pacientes);

    var headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=pacientes.pdf");

    return ResponseEntity
      .ok()
      .headers(headers)
      .contentType(MediaType.APPLICATION_PDF)
      .body(new InputStreamResource(bis));
  }

  private List<Paciente> buscaMedicoNoBancoOrElseThrow(Long id) {
    return pacienteRepository.buscaPacientesDoMedico(
      medicoRepository
        .findById(id)
        .orElseThrow(
          () ->
            new ResponseStatusException(
              HttpStatus.NOT_FOUND,
              "Médico não encontrado"
            )
        )
    );
  }

  private Paciente buscaPacientePorIdOrElseThrow(Long id) {
    return pacienteRepository
      .findById(id)
      .orElseThrow(
        () ->
          new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Paciente não encontrado"
          )
      );
  }

  private boolean verificaSePacientePossuiAtendimento(Long id) {
    return atendimentoRepository.existsByPacienteId(id);
  }
}
