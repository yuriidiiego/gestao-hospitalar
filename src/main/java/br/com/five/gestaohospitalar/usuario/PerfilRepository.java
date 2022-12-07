package br.com.five.gestaohospitalar.usuario;

import br.com.five.gestaohospitalar.enums.TipoPerfil;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
  Optional<Perfil> findByNome(TipoPerfil nome);
}
