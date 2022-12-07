package br.com.five.gestaohospitalar.config.security.jwt;

import br.com.five.gestaohospitalar.usuario.UsuarioDetailsServiceImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtService jwtUtils;

  @Autowired
  private UsuarioDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(
    HttpServletRequest requisicao,
    HttpServletResponse resposta,
    FilterChain filtroDeCadeia
  )
    throws ServletException, IOException {
    try {
      String jwt = analisaTokenJWT(requisicao);
      if (jwt != null && jwtUtils.validaOToken(jwt)) {
        String username = jwtUtils.buscaOUsuarioDoToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(
          username
        );

        UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );

        autenticacao.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(requisicao)
        );

        SecurityContextHolder.getContext().setAuthentication(autenticacao);
      }
    } catch (Exception e) {
      logger.error("Não foi possível definir o usuário autenticado: {}", e);
    }

    filtroDeCadeia.doFilter(requisicao, resposta); 
  }

  private String analisaTokenJWT(HttpServletRequest request) {
    return jwtUtils.buscarTokenDoCookie(request);
  }
}
