package br.com.five.gestaohospitalar.medico;

import br.com.five.gestaohospitalar.config.error.ErrorResponse;
import br.com.five.gestaohospitalar.medico.payload.request.MedicoPatchRequest;
import br.com.five.gestaohospitalar.medico.payload.request.MedicoPostRequest;
import br.com.five.gestaohospitalar.medico.payload.request.MedicoPutRequest;
import br.com.five.gestaohospitalar.medico.payload.response.MedicoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Médico", description = "Endpoints para gerenciamento de médicos")
@RestController
@RequestMapping("/medicos")
public class MedicoController {
  @Autowired
  private IMedicoService medicoService;

  @Operation(
    summary = "Cadastra um médico",
    operationId = "salvarMedico",
    description = "Cadastra um médico no sistema",
    responses = {
      @ApiResponse(
        responseCode = "201",
        description = "Médico cadastrado com sucesso",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = MedicoResponse.class)
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
    }
  )
  @PostMapping
  public ResponseEntity<MedicoResponse> salvarMedico(
    @RequestBody @Valid MedicoPostRequest medicoPostDTO,
    UriComponentsBuilder uriComponentsBuilder
  ) {
    MedicoResponse medicoResponseDTO = medicoService.salvar(medicoPostDTO);
    return ResponseEntity
      .created(
        uriComponentsBuilder
          .path("/medicos/{id}")
          .buildAndExpand(medicoResponseDTO.getId())
          .toUri()
      )
      .body(medicoResponseDTO);
  }

  @Operation(
    summary = "Busca médico por ID",
    operationId = "buscaMedicoPorId",
    description = "Retorna um médico pelo ID",
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Médico encontrado",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = MedicoResponse.class)
          ),
        }
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Médico não encontrado",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
    }
  )
  @GetMapping("/{id}")
  public ResponseEntity<MedicoResponse> buscaMedicoPorId(
    @Parameter(
      description = "ID do médico",
      example = "1"
    ) @PathVariable Long id
  ) {
    return ResponseEntity.ok(medicoService.buscarPorId(id));
  }

  @Operation(
    summary = "Busca todos os médicos",
    operationId = "buscarTodos",
    description = "Retorna todos os médicos cadastrados",
    responses = @ApiResponse(
      responseCode = "200",
      description = "Médicos encontrados",
      content = {
        @Content(
          mediaType = "application/json",
          array = @ArraySchema(
            schema = @Schema(implementation = MedicoResponse.class)
          )
        ),
      }
    )
  )
  @GetMapping
  public ResponseEntity<List<MedicoResponse>> buscarTodos() {
    return ResponseEntity.ok(medicoService.buscarTodos());
  }

  @Operation(
    summary = "Lista médicos que trabalharam em um período de datas",
    operationId = "buscarPorPeriodo",
    description = "Retorna médicos que trabalharam em um período de datas",
    responses = @ApiResponse(
      responseCode = "200",
      description = "Médicos encontrados",
      content = {
        @Content(
          mediaType = "application/json",
          array = @ArraySchema(
            schema = @Schema(implementation = MedicoResponse.class)
          )
        ),
      }
    )
  )
  @GetMapping("/periodo")
  public ResponseEntity<List<MedicoResponse>> buscaPorPeriodo(
    @Parameter(
      description = "Data inicial do período",
      example = "01/01/2022"
    ) @RequestParam LocalDate dataInicio,
    @Parameter(
      description = "Data final do período",
      example = "31/12/2022"
    ) @RequestParam LocalDate dataFim
  ) {
    return ResponseEntity.ok(
      medicoService.listaMedicosPorPeriodo(dataInicio, dataFim)
    );
  }

  @Operation(
    summary = "Lista médicos que atenderam um determinado paciente",
    operationId = "buscarPorPaciente"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Médicos encontrados com sucesso",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = MedicoResponse.class)
          ),
        }
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Paciente não encontrado",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
      @ApiResponse(
        responseCode = "500",
        description = "Erro interno",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
    }
  )
  @GetMapping("/pacientes/{idPaciente}")
  public ResponseEntity<List<MedicoResponse>> buscaPorPaciente(
    @Parameter(
      description = "ID do paciente",
      example = "1"
    ) @PathVariable Long idPaciente
  ) {
    return ResponseEntity.ok(
      medicoService.listaMedicosPorPacienteID(idPaciente)
    );
  }

  @Operation(summary = "Atualiza médico", operationId = "atualizar")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Médico atualizado com sucesso",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = MedicoResponse.class)
          ),
        }
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Médico não encontrado",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
      @ApiResponse(
        responseCode = "500",
        description = "Erro interno",
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
  public ResponseEntity<MedicoResponse> atualizar(
    @Parameter(
      description = "ID do médico",
      example = "1"
    ) @PathVariable Long id,
    @RequestBody @Valid MedicoPutRequest medicoPutDTO
  ) {
    return ResponseEntity.ok(medicoService.atualizar(id, medicoPutDTO));
  }

  @Operation(
    summary = "Atualiza parcialmente um médico",
    operationId = "atualizarParcial"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Médico atualizado com sucesso",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = MedicoResponse.class)
          ),
        }
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Médico não encontrado",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
      @ApiResponse(
        responseCode = "500",
        description = "Erro interno",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
    }
  )
  @PatchMapping("/{id}")
  public ResponseEntity<MedicoResponse> atualizarParcial(
    @Parameter(
      description = "ID do médico",
      example = "1"
    ) @PathVariable Long id,
    @RequestBody @Valid MedicoPatchRequest medicoPatchDTO
  ) {
    return ResponseEntity.ok(
      medicoService.atualizarParcialmente(id, medicoPatchDTO)
    );
  }

  @Operation(summary = "Deleta um médico", operationId = "deletar")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "204",
        description = "Médico deletado com sucesso"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Médico não encontrado",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
      @ApiResponse(
        responseCode = "500",
        description = "Erro interno",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
          ),
        }
      ),
    }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(
    @Parameter(
      description = "ID do médico",
      example = "1"
    ) @PathVariable Long id
  ) {
    medicoService.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
