package com.example.v2_ArqWeb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema Acadêmico - API")
                        .version("1.0")
                        .description("Documentação da API do sistema acadêmico contendo endpoints de alunos e cursos."));
    }
}
