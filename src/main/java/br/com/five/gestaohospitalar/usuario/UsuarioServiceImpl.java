package br.com.five.gestaohospitalar.usuario;

import br.com.five.gestaohospitalar.config.security.jwt.JwtService;
import br.com.five.gestaohospitalar.enums.TipoPerfil;
import br.com.five.gestaohospitalar.usuario.payload.request.UsuarioCadastroRequest;
import br.com.five.gestaohospitalar.usuario.payload.request.UsuarioLoginRequest;
import br.com.five.gestaohospitalar.usuario.payload.response.MensagemResponse;
import br.com.five.gestaohospitalar.usuario.payload.response.UsuariorInfoResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
  private static final String PERFIL_NOT_FOUND = "Perfil não encontrado";

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PerfilRepository perfilRepository;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private JwtService jwtSertvice;

  @Override
  public ResponseEntity<UsuariorInfoResponse> login(
    UsuarioLoginRequest loginRequest
  ) {
    Authentication autenticacao = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginRequest.getNomeUsuario(),
        loginRequest.getSenha()
      )
    );

    SecurityContextHolder.getContext().setAuthentication(autenticacao);

    UsuarioDetailsImpl usuarioDetails = (UsuarioDetailsImpl) autenticacao.getPrincipal();

    ResponseCookie jwtCookie = jwtSertvice.geraCookieComToken(usuarioDetails);

    String valueCookie = jwtCookie.getValue();

    List<String> perfis = usuarioDetails
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toList());

    return ResponseEntity
      .ok()
      .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
      .body(
        new UsuariorInfoResponse(
          usuarioDetails.getId(),
          usuarioDetails.getUsername(),
          usuarioDetails.getEmail(),
          valueCookie,
          perfis
        )
      );
  }

  @Override
  public ResponseEntity<MensagemResponse> cadastro(
    UsuarioCadastroRequest cadastroRequest
  ) {
    Usuario usuario = new Usuario(
      cadastroRequest.getNomeUsuario(),
      cadastroRequest.getEmail(),
      encoder.encode(cadastroRequest.getSenha())
    );

    Set<String> strPerfis = cadastroRequest.getPerfil();
    Set<Perfil> perfis = new HashSet<>();

    if (strPerfis == null) {
      Perfil usuarioPerfil = perfilRepository
        .findByNome(TipoPerfil.ROLE_USER)
        .orElseThrow(() -> new RuntimeException(PERFIL_NOT_FOUND));
      perfis.add(usuarioPerfil);
    } else {
      strPerfis.forEach(
        perfil -> {
          switch (perfil) {
            case "admin":
              Perfil adminPerfil = perfilRepository
                .findByNome(TipoPerfil.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException(PERFIL_NOT_FOUND));
              perfis.add(adminPerfil);

              break;
            case "mod":
              Perfil modPerfil = perfilRepository
                .findByNome(TipoPerfil.ROLE_MODERATOR)
                .orElseThrow(() -> new RuntimeException(PERFIL_NOT_FOUND));
              perfis.add(modPerfil);

              break;
            default:
              Perfil usuarioPerfil = perfilRepository
                .findByNome(TipoPerfil.ROLE_USER)
                .orElseThrow(() -> new RuntimeException(PERFIL_NOT_FOUND));
              perfis.add(usuarioPerfil);
          }
        }
      );
    }

    usuario.setPerfis(perfis);
    usuarioRepository.save(usuario);

    return ResponseEntity.ok(
      new MensagemResponse("Usuário registrado com sucesso!")
    );
  }

  @Override
  public ResponseEntity<MensagemResponse> logout() {
    ResponseCookie cookie = jwtSertvice.limpaOTokenDoCookie();
    return ResponseEntity
      .ok()
      .header(HttpHeaders.SET_COOKIE, cookie.toString())
      .body(new MensagemResponse("Usuário deslogado com sucesso!"));
  }
}
