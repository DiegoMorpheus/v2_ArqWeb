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
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

    // 🔑 Encoder de senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 👥 Usuários em memória (apenas para testes)
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

    // 🔒 Configuração de segurança + CORS
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            // 🚀 Configuração de CORS
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration corsConfig = new CorsConfiguration();

                // 👉 Libere o frontend do Vite (porta 5173)
                corsConfig.setAllowedOrigins(List.of("http://localhost:5173"));

                // 👉 Se quiser liberar tudo durante desenvolvimento, use:
                // corsConfig.setAllowedOriginPatterns(List.of("*"));

                corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                corsConfig.setAllowedHeaders(List.of("*"));
                corsConfig.setAllowCredentials(true);
                return corsConfig;
            }))

            .authorizeHttpRequests(auth -> auth
                // Endpoints liberados sem login
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/h2-console/**",
                    "/actuator/**"
                ).permitAll()

                // Endpoints protegidos
                .requestMatchers("/alunos/**", "/cursos/**").hasRole("ADMIN")
                .requestMatchers("/alunos", "/cursos").hasAnyRole("USER", "ADMIN")

                .anyRequest().authenticated()
            )

            // Login via formulário
            .formLogin(form -> form
                .defaultSuccessUrl("/swagger-ui/index.html", true)
                .permitAll()
            )

            // Autenticação básica
            .httpBasic(Customizer.withDefaults())

            // Necessário para console H2
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
