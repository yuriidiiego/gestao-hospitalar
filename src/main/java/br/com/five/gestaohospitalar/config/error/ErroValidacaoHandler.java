package br.com.five.gestaohospitalar.config.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErroValidacaoHandler extends ResponseEntityExceptionHandler {
  @Autowired
  private MessageSource messageSource;

  @ExceptionHandler({ BadCredentialsException.class })
  public ResponseEntity<Object> handleBadCredentialsException(
    BadCredentialsException ex,
    WebRequest request
  ) {
    return handleExceptionInternal(
      ex,
      new ErrorResponse(
        HttpStatus.UNAUTHORIZED.value(),
        "Usuário ou senha inválidos"
      ),
      new HttpHeaders(),
      HttpStatus.UNAUTHORIZED,
      request
    );
  }

  @ExceptionHandler({ AuthenticationException.class })
  public ResponseEntity<Object> handleAuthenticationException(
    AuthenticationException ex,
    WebRequest request
  ) {
    return handleExceptionInternal(
      ex,
      new ErrorResponse(
        HttpStatus.UNAUTHORIZED.value(),
        "Voce não tem permissão para acessar esse recurso"
      ),
      new HttpHeaders(),
      HttpStatus.UNAUTHORIZED,
      request
    );
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
    TypeMismatchException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    int statuscode = HttpStatus.BAD_REQUEST.value();
    String mensagem = ex.getMessage();
    return new ResponseEntity<>(
      new ErrorResponse(statuscode, mensagem),
      HttpStatus.BAD_REQUEST
    );
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    List<ErrorResponse> dto = new ArrayList<>();
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    fieldErrors.forEach(
      e -> {
        String mensagem = messageSource.getMessage(
          e,
          LocaleContextHolder.getLocale()
        );
        int statuscode = HttpStatus.BAD_REQUEST.value();
        ErrorResponse erro = new ErrorResponse(statuscode, mensagem);
        dto.add(erro);
      }
    );
    return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
    HttpRequestMethodNotSupportedException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    int statuscode = HttpStatus.METHOD_NOT_ALLOWED.value();
    String metodo = ex.getMethod();
    String path = ex.getSupportedHttpMethods().toString();
    String mensagem = String.format(
      "O método %s não é suportado para esta requisição. Os métodos suportados são %s",
      metodo,
      path
    );
    return new ResponseEntity<>(
      new ErrorResponse(statuscode, mensagem),
      HttpStatus.METHOD_NOT_ALLOWED
    );
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
    HttpMessageNotReadableException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    if (
      ex.contains(InvalidFormatException.class) ||
      ex.contains(DateTimeParseException.class)
    ) {
      int statuscode = HttpStatus.BAD_REQUEST.value();
      String mensagem = "Formato de data inválido. Formato aceito: dd/MM/yyyy";
      return new ResponseEntity<>(
        new ErrorResponse(statuscode, mensagem),
        HttpStatus.BAD_REQUEST
      );
    }
    int statuscode = HttpStatus.BAD_REQUEST.value();

    return new ResponseEntity<>(
      new ErrorResponse(statuscode, ex.getMessage()),
      HttpStatus.BAD_REQUEST
    );
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
    MissingServletRequestParameterException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    int statuscode = HttpStatus.BAD_REQUEST.value();
    String mensagem = "O parâmetro " + ex.getParameterName() + " é obrigatório";
    return new ResponseEntity<>(
      new ErrorResponse(statuscode, mensagem),
      HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrityViolationException(
    DataIntegrityViolationException ex
  ) {
    int statuscode = HttpStatus.CONFLICT.value();
    String rootCause =
      ((ConstraintViolationException) ex.getCause()).getSQLException()
        .getMessage();
    String mensagem = "Erro de integridade de dados: " + rootCause;
    return new ResponseEntity<>(
      new ErrorResponse(statuscode, mensagem),
      HttpStatus.CONFLICT
    );
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Object> handleResponseStatusException(
    ResponseStatusException ex
  ) {
    int statuscode = ex.getStatus().value();
    String mensagem = ex.getReason();
    return new ResponseEntity<>(
      new ErrorResponse(statuscode, mensagem),
      ex.getStatus()
    );
  }
}
