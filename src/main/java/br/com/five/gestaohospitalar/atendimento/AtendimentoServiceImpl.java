package br.com.five.gestaohospitalar.atendimento;

import br.com.five.gestaohospitalar.atendimento.payload.request.AtendimentoPostRequest;
import br.com.five.gestaohospitalar.atendimento.payload.request.AtendimentoPutRequest;
import br.com.five.gestaohospitalar.atendimento.payload.response.AtendimentoResponse;
import br.com.five.gestaohospitalar.medico.Medico;
import br.com.five.gestaohospitalar.medico.MedicoRepository;
import br.com.five.gestaohospitalar.paciente.Paciente;
import br.com.five.gestaohospitalar.paciente.PacienteRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AtendimentoServiceImpl implements IAtendimentoService {
  @Autowired
  private AtendimentoRepository atendimentoRepository;

  @Autowired
  private AtendimentoMapper atendimentoMapper;

  @Autowired
  private MedicoRepository medicoRepository;

  @Autowired
  private PacienteRepository pacienteRepository;

  @Override
  public AtendimentoResponse salvar(AtendimentoPostRequest atendimentoPostDTO) {
    Medico medico = buscaMedicoPorIdOrElseThrow(atendimentoPostDTO);
    Paciente paciente = buscaPacientePorIdOrElseThrow(atendimentoPostDTO);

    return transformaDTOEmEntidadeESalvaNoBanco(
      atendimentoPostDTO,
      medico,
      paciente
    );
  }

  @Override
  public AtendimentoResponse atualizar(
    Long id,
    AtendimentoPutRequest atendimentoPutDTO
  ) {
    return atendimentoRepository
      .findById(id)
      .map(
        atendimento -> {
          atendimentoMapper.toAtendimento(atendimentoPutDTO, atendimento);
          return atendimentoMapper.toAtendimentoResponse(
            atendimentoRepository.save(atendimento)
          );
        }
      )
      .orElseThrow(
        () ->
          new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Atendimento não encontrado"
          )
      );
  }

  @Override
  public List<AtendimentoResponse> buscarPorPeriodo(
    LocalDate dataInicio,
    LocalDate dataFim
  ) {
    return atendimentoMapper.toAtendimentoResponse(
      atendimentoRepository.buscaAtendimentosPorPeriodo(dataInicio, dataFim)
    );
  }

  private AtendimentoResponse transformaDTOEmEntidadeESalvaNoBanco(
    AtendimentoPostRequest atendimentoPostDTO,
    Medico medico,
    Paciente paciente
  ) {
    return atendimentoMapper.toAtendimentoResponse(
      atendimentoRepository.save(
        atendimentoMapper.toAtendimento(atendimentoPostDTO, medico, paciente)
      )
    );
  }

  private Paciente buscaPacientePorIdOrElseThrow(
    AtendimentoPostRequest atendimentoPostDTO
  ) {
    return pacienteRepository
      .findById(atendimentoPostDTO.getIdPaciente())
      .orElseThrow(
        () ->
          new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Paciente não encontrado"
          )
      );
  }

  private Medico buscaMedicoPorIdOrElseThrow(
    AtendimentoPostRequest atendimentoPostDTO
  ) {
    return medicoRepository
      .findById(atendimentoPostDTO.getIdMedico())
      .orElseThrow(
        () ->
          new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Médico não encontrado"
          )
      );
  }
}
