package br.com.five.gestaohospitalar.config.security.jwt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component("jwtAuthEntryPoint")
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;

  @Override
  public void commence(
    HttpServletRequest requisicao,
    HttpServletResponse resposta,
    AuthenticationException autenticacaoException
  )
    throws IOException, ServletException {
    resolver.resolveException(requisicao, resposta, null, autenticacaoException);
  }
}
