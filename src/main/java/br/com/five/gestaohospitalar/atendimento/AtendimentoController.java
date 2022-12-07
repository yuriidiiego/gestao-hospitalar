package br.com.five.gestaohospitalar.atendimento;

import br.com.five.gestaohospitalar.atendimento.payload.request.AtendimentoPostRequest;
import br.com.five.gestaohospitalar.atendimento.payload.request.AtendimentoPutRequest;
import br.com.five.gestaohospitalar.atendimento.payload.response.AtendimentoResponse;
import br.com.five.gestaohospitalar.config.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(
  name = "Atendimento",
  description = "Endpoints para gerenciamento de atendimentos"
)
@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {
  @Autowired
  private IAtendimentoService atendimentoService;

  @Operation(
    summary = "Cadastra um atendimento",
    operationId = "salvarAtendimento"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "201",
        description = "Atendimento criado com sucesso",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AtendimentoResponse.class)
          ),
        }
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Erro de validação",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Paciente ou médico não encontrado",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
    }
  )
  @PostMapping
  public ResponseEntity<AtendimentoResponse> salvarAtendimento(
    @RequestBody @Valid AtendimentoPostRequest atendimentoPostDTO,
    UriComponentsBuilder uriBuilder
  ) {
    AtendimentoResponse atendimentoResponseDTO = atendimentoService.salvar(
      atendimentoPostDTO
    );
    return ResponseEntity
      .created(
        uriBuilder
          .path("/atendimentos/{id}")
          .buildAndExpand(atendimentoResponseDTO.getId())
          .toUri()
      )
      .body(atendimentoResponseDTO);
  }

  @Operation(
    summary = "Atualiza um atendimento",
    operationId = "atualizarAtendimento"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Atendimento atualizado com sucesso",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AtendimentoResponse.class)
          ),
        }
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Erro de validação",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Atendimento, paciente ou médico não encontrado",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
    }
  )
  @PutMapping("/{id}")
  public ResponseEntity<AtendimentoResponse> atualizarAtendimento(
    @Parameter(
      description = "ID do atendimento",
      example = "1"
    ) @PathVariable Long id,
    @RequestBody @Valid AtendimentoPutRequest atendimentoPutDTO
  ) {
    return ResponseEntity.ok(
      atendimentoService.atualizar(id, atendimentoPutDTO)
    );
  }

  @Operation(
    summary = "Busca atendimentos por data",
    operationId = "buscarPorPeriodo"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Operação realizada com sucesso",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AtendimentoResponse.class)
          ),
        }
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Formato de data inválido. Formato aceito: dd/MM/yyyy",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
    }
  )
  @GetMapping("/periodo")
  public ResponseEntity<List<AtendimentoResponse>> buscarPorPeriodo(
    @Parameter(
      description = "Data inicial do período. Formato aceito: dd/MM/yyyy",
      example = "01/01/2022"
    ) @RequestParam LocalDate dataInicio,
    @Parameter(
      description = "Data final do período. Formato aceito: dd/MM/yyyy",
      example = "31/12/2022"
    ) @RequestParam LocalDate dataFim
  ) {
    return ResponseEntity.ok(
      atendimentoService.buscarPorPeriodo(dataInicio, dataFim)
    );
  }
}
