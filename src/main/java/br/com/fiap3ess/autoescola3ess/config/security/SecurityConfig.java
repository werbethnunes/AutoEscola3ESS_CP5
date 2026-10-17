
package br.com.fiap3ess.autoescola3ess.config.security;

import br.com.fiap3ess.autoescola3ess.config.security.filter.SecurityFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration configuration = new CorsConfiguration();

                            configuration.setAllowedOriginPatterns(Arrays.asList(
                                    "https://www.enderecodomeufront.com.br",
                                    "http://128.255.10.28:8080",
                                    "http://localhost:3000",
                                    "http://127.0.0.1:3000",
                                    "http://localhost:5500",
                                    "http://127.0.0.1:5500"
                            ));

                            configuration.setAllowedMethods(List.of(
                                    "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"
                            ));

                            configuration.setAllowedHeaders(List.of(
                                    "Authorization", "Content-Type", "Accept", "Origin"
                            ));

                            configuration.setAllowCredentials(true);
                            return configuration;
                        }))
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // Forward interno para /error (ex.: JSON inválido -> 400). A requisição original
                        // já foi autorizada; sem isso o SecurityFilter não roda no dispatch ERROR e o
                        // erro real é mascarado como 403.
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()

                        // Login e verificação de saúde públicos
                        .requestMatchers("/login", "/health-check").permitAll()

                        // Swagger e documentação públicos
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/webjars/**"
                        ).permitAll()

                        // Permissões dos instrutores
                        .requestMatchers(HttpMethod.POST, "/instrutores")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/instrutores")
                        .hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.GET, "/instrutores/{id}")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/instrutores")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/instrutores/{id}")
                        .hasRole("ADMIN")

                        // O agendamento permanece disponível para os perfis da aplicação.
                        .requestMatchers(HttpMethod.POST, "/instrucoes")
                        .hasAnyRole("ADMIN", "USER")

                        // Demais endpoints continuam protegidos
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        securityFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
