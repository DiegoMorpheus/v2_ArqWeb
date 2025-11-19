package com.example.v2_ArqWeb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Encoder de senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Usuários em memória
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    // Configuração de Segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                // LIBERADOS
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/h2-console/**",
                    "/actuator/**"   // <--- LIBERANDO ACTUATOR + PROMETHEUS
                ).permitAll()

                // PROTEGIDOS
                .requestMatchers("/alunos/**", "/cursos/**").hasRole("ADMIN")
                .requestMatchers("/alunos", "/cursos").hasAnyRole("USER", "ADMIN")

                .anyRequest().authenticated()
            )

            // Login
            .formLogin(form -> form
                .defaultSuccessUrl("/swagger-ui/index.html", true)
                .permitAll()
            )

            // Autenticação básica (útil para Prometheus, Grafana, scripts etc)
            .httpBasic(Customizer.withDefaults())

            // Necessário para console H2
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
