package br.com.five.gestaohospitalar.paciente;

import br.com.five.gestaohospitalar.dadopessoal.DadoPessoalRepository;
import br.com.five.gestaohospitalar.medico.Medico;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends DadoPessoalRepository<Paciente> {
  @Query(
    "SELECT p FROM Paciente p JOIN p.atendimentos a WHERE a.medico = :medico"
  )
  List<Paciente> buscaPacientesDoMedico(@Param("medico") Medico medico);
}
