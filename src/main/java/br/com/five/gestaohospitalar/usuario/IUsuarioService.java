package br.com.five.gestaohospitalar.usuario;

import br.com.five.gestaohospitalar.usuario.payload.request.UsuarioCadastroRequest;
import br.com.five.gestaohospitalar.usuario.payload.request.UsuarioLoginRequest;
import br.com.five.gestaohospitalar.usuario.payload.response.MensagemResponse;
import br.com.five.gestaohospitalar.usuario.payload.response.UsuariorInfoResponse;
import org.springframework.http.ResponseEntity;

public interface IUsuarioService {
  ResponseEntity<UsuariorInfoResponse> login(
      UsuarioLoginRequest usuarioLoginRequest);

  ResponseEntity<MensagemResponse> cadastro(
      UsuarioCadastroRequest usuarioCadastroRequest);

  ResponseEntity<MensagemResponse> logout();
}
