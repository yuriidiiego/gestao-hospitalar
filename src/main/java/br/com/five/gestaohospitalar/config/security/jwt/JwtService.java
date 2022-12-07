package br.com.five.gestaohospitalar.config.security.jwt;

import br.com.five.gestaohospitalar.usuario.UsuarioDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.Duration;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class JwtService {
  private static final Logger logger = LoggerFactory.getLogger(
    JwtService.class
  );

  @Value("${app.jwtSecret}")
  private String segredoDoTokenJWT;

  @Value("${app.jwtExpirationMs}")
  private int jwtExpiracaoEmMilisegundos;

  @Value("${app.jwtCookieName}")
  private String nomeDoCookieJWT;

  public String buscarTokenDoCookie(HttpServletRequest requisicao) {
    Cookie cookie = WebUtils.getCookie(requisicao, nomeDoCookieJWT);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return null;
    }
  }

  public ResponseCookie geraCookieComToken(UsuarioDetailsImpl usuarioPrincipal) {
    String jwt = geraTokenDoUsuario(usuarioPrincipal.getUsername());
    return ResponseCookie
      .from(nomeDoCookieJWT, jwt)
      .path("/")
      .maxAge(Duration.ofMinutes(10))
      .httpOnly(true)
      .build();
  }

  public ResponseCookie limpaOTokenDoCookie() {
    return ResponseCookie.from(nomeDoCookieJWT, "").path("/").build();
  }

  public String buscaOUsuarioDoToken(String token) {
    return Jwts
      .parser()
      .setSigningKey(segredoDoTokenJWT)
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }

  public boolean validaOToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(segredoDoTokenJWT).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Assinatura inválida do JWT: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Token JWT inválido: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("O token JWT está expirado: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("O token JWT não é suportado: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error(
        "A string de declarações do JWT está vazia: {}",
        e.getMessage()
      );
    }

    return false;
  }

  public String geraTokenDoUsuario(String usuario) {
    return Jwts
      .builder()
      .setSubject(usuario)
      .setIssuedAt(new Date())
      .setExpiration(
        new Date((new Date()).getTime() + jwtExpiracaoEmMilisegundos)
      )
      .signWith(SignatureAlgorithm.HS512, segredoDoTokenJWT)
      .compact();
  }
}
