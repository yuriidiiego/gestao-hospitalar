package br.com.five.gestaohospitalar.usuario;

import br.com.five.gestaohospitalar.config.error.ErrorResponse;
import br.com.five.gestaohospitalar.usuario.payload.request.UsuarioCadastroRequest;
import br.com.five.gestaohospitalar.usuario.payload.request.UsuarioLoginRequest;
import br.com.five.gestaohospitalar.usuario.payload.response.MensagemResponse;
import br.com.five.gestaohospitalar.usuario.payload.response.UsuariorInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
  name = "Usuário",
  description = "Endpoints para cadastar, logar e autenticar usuários para acessar outros endpoints"
)
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
  @Autowired
  private IUsuarioService service;

  @PostMapping("/login")
  @Operation(
    summary = "Loga um usuário",
    operationId = "login",
    description = "Loga um usuário e retorna um token JWT no cookie"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Usuário logado com sucesso"
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Não autorizado",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
    }
  )
  public ResponseEntity<UsuariorInfoResponse> login(
    @Valid @RequestBody UsuarioLoginRequest loginRequest
  ) {
    return service.login(loginRequest);
  }

  @PostMapping("/cadastro")
  @Operation(
    summary = "Cadastra um usuário",
    operationId = "cadastro",
    description = "Cadastra um usuário no sistema"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Usuário cadastrado com sucesso"
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Email ou nome de usuário já cadastrados",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
    }
  )
  public ResponseEntity<MensagemResponse> cadastro(
    @Valid @RequestBody UsuarioCadastroRequest cadastroRequest
  ) {
    return service.cadastro(cadastroRequest);
  }

  @PostMapping("/logout")
  @Operation(
    summary = "Desloga um usuário",
    operationId = "logout",
    description = "Desloga um usuário limpando o cookie com o token JWT"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Usuário deslogado com sucesso"
      ),
    }
  )
  public ResponseEntity<MensagemResponse> logout() {
    return service.logout();
  }
}
