package br.com.five.gestaohospitalar.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByNomeUsuario(String nomeUsuario);

  Boolean existsByNomeUsuario(String nomeUsuario);

  Boolean existsByEmail(String email);
}
