package br.com.five.gestaohospitalar.paciente;

import br.com.five.gestaohospitalar.config.error.ErrorResponse;
import br.com.five.gestaohospitalar.paciente.payload.request.PacientePatchRequest;
import br.com.five.gestaohospitalar.paciente.payload.request.PacientePostRequest;
import br.com.five.gestaohospitalar.paciente.payload.request.PacientePutRequest;
import br.com.five.gestaohospitalar.paciente.payload.response.PacienteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(
  name = "Paciente",
  description = "Endpoints para gerenciamento de pacientes"
)
@RestController
@RequestMapping("/pacientes")
public class PacienteController {
  @Autowired
  private IPacienteService pacienteService;

  @Operation(summary = "Cadastra um paciente", operationId = "salvar")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "201",
        description = "Paciente cadastrado com sucesso",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PacienteResponse.class)
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
  public ResponseEntity<PacienteResponse> salvar(
    @RequestBody @Valid PacientePostRequest pacientePostDTO,
    UriComponentsBuilder uriComponentsBuilder
  ) {
    PacienteResponse pacienteResponseDTO = pacienteService.salvar(
      pacientePostDTO
    );
    return ResponseEntity
      .created(
        uriComponentsBuilder
          .path("/pacientes/{id}")
          .buildAndExpand(pacienteResponseDTO.getId())
          .toUri()
      )
      .body(pacienteResponseDTO);
  }

  @Operation(summary = "Busca paciente por id", operationId = "buscarPorId")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Paciente encontrado com sucesso",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PacienteResponse.class)
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
    }
  )
  @GetMapping("/{id}")
  public ResponseEntity<PacienteResponse> buscarPorId(
    @Parameter(
      description = "Id do paciente",
      example = "1"
    ) @PathVariable Long id
  ) {
    return ResponseEntity.ok(pacienteService.buscarPorId(id));
  }

  @Operation(summary = "Busca todos os pacientes", operationId = "buscarTodos")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Pacientes encontrados com sucesso",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PacienteResponse.class)
          ),
        }
      ),
    }
  )
  @GetMapping
  public ResponseEntity<List<PacienteResponse>> buscarTodos() {
    return ResponseEntity.ok(pacienteService.buscarTodos());
  }

  @Operation(
    summary = " Lista os pacientes de um determinado médico",
    operationId = "buscarPorMedico"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Pacientes encontrados com sucesso",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PacienteResponse.class)
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
  @GetMapping("/medicos/{idMedico}")
  public ResponseEntity<List<PacienteResponse>> buscarPorMedico(
    @Parameter(
      description = "Id do médico",
      example = "1"
    ) @PathVariable Long idMedico
  ) {
    return ResponseEntity.ok(
      pacienteService.listaPacientePorMedicoID(idMedico)
    );
  }

  @Operation(
    summary = "gera um pdf com todos os pacientes",
    operationId = "gerarPdfPacientes"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "pdf gerado com sucesso",
        content = @Content(mediaType = "application/pdf")
      ),
      @ApiResponse(
        responseCode = "500",
        description = "erro interno no servidor",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)
        )
      ),
    }
  )
  @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<InputStreamResource> gerarPdfPacientes() {
    return pacienteService.gerarPdfPacientes();
  }

  @Operation(summary = "Atualiza um paciente", operationId = "atualizar")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Paciente atualizado com sucesso",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PacienteResponse.class)
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
        description = "Paciente não encontrado",
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
  public ResponseEntity<PacienteResponse> atualizar(
    @Parameter(
      description = "Id do paciente",
      example = "1"
    ) @PathVariable Long id,
    @RequestBody @Valid PacientePutRequest pacientePutDTO
  ) {
    return ResponseEntity.ok(pacienteService.atualizar(id, pacientePutDTO));
  }

  @Operation(
    summary = "Atualiza parcialmente um paciente",
    operationId = "atualizarParcial"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Paciente atualizado com sucesso",
        content = {
          @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PacienteResponse.class)
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
        description = "Paciente não encontrado",
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
  public ResponseEntity<PacienteResponse> atualizarParcial(
    @Parameter(
      description = "Id do paciente",
      example = "1"
    ) @PathVariable Long id,
    @RequestBody @Valid PacientePatchRequest pacientePatchDTO
  ) {
    return ResponseEntity.ok(
      pacienteService.atualizarParcialmente(id, pacientePatchDTO)
    );
  }

  @Operation(summary = "Deleta um paciente", operationId = "deletar")
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "204",
        description = "Paciente deletado com sucesso",
        content = @Content
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
    }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(
    @Parameter(
      description = "Id do paciente",
      example = "1"
    ) @PathVariable Long id
  ) {
    pacienteService.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
