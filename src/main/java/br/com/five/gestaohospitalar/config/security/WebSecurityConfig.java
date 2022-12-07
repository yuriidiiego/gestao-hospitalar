package br.com.five.gestaohospitalar.config.security;

import br.com.five.gestaohospitalar.config.security.jwt.JwtAuthTokenFilter;
import br.com.five.gestaohospitalar.usuario.UsuarioDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
  private static final String ROLE_USER = "USER";
  private static final String ROLE_ADMIN = "ADMIN";

  private final UsuarioDetailsServiceImpl userDetailsService;

  @Qualifier("jwtAuthEntryPoint")
  private final AuthenticationEntryPoint unauthorizedHandler;

  public WebSecurityConfig(
    UsuarioDetailsServiceImpl userDetailsService,
    AuthenticationEntryPoint unauthorizedHandler
  ) {
    this.userDetailsService = userDetailsService;
    this.unauthorizedHandler = unauthorizedHandler;
  }

  @Bean
  public JwtAuthTokenFilter authenticationJwtTokenFilter() {
    return new JwtAuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authConfig
  )
    throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors()
      .and()
      .csrf()
      .disable()
      .exceptionHandling()
      .authenticationEntryPoint(unauthorizedHandler)
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeHttpRequests(
        authorizeHttpRequests ->
          authorizeHttpRequests
            .antMatchers("/usuarios/**", "/gestao-hospitalar/**")
            .permitAll()
            .antMatchers("/medicos/**")
            .hasAnyRole(ROLE_USER, ROLE_ADMIN)
            .antMatchers("/pacientes/**")
            .hasAnyRole(ROLE_USER, ROLE_ADMIN)
            .antMatchers("/atendimentos/**")
            .hasAnyRole(ROLE_USER, ROLE_ADMIN)
            .anyRequest()
            .authenticated()
      );

    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(
      authenticationJwtTokenFilter(),
      UsernamePasswordAuthenticationFilter.class
    );

    return http.build();
  }
}
