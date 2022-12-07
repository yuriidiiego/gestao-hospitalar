package br.com.five.gestaohospitalar.dadopessoal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DadoPessoalRepository<T extends DadoPessoal>
  extends JpaRepository<T, Long> {
  boolean existsByCpf(String cpf);
}
