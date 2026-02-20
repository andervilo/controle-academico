package br.com.sdd.controleacademico.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI controleAcademicoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Controle Acadêmico API")
                        .description("API para gerenciamento de controle acadêmico")
                        .version("v1.0.0"));
    }
}
