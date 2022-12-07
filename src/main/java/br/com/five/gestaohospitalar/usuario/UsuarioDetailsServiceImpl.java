package br.com.five.gestaohospitalar.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UsuarioRepository usuarioRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String nomeDeUsuario)
    throws UsernameNotFoundException {
    return UsuarioDetailsImpl.build(
      usuarioRepository
        .findByNomeUsuario(nomeDeUsuario)
        .orElseThrow(
          () -> new UsernameNotFoundException("Usuário não encontrado")
        )
    );
  }
}
