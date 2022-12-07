package br.com.five.gestaohospitalar.atendimento;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendimentoRepository
  extends JpaRepository<Atendimento, Long> {
  @Query(
    "SELECT a FROM Atendimento a WHERE a.dataAtendimento BETWEEN :dataInicio AND :dataFim"
  )
  List<Atendimento> buscaAtendimentosPorPeriodo(
    @Param("dataInicio") LocalDate dataInicio,
    @Param("dataFim") LocalDate dataFim
  );

  boolean existsByPacienteId(Long id);

  boolean existsByMedicoId(Long id);
}
