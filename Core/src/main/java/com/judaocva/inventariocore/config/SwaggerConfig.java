package com.judaocva.inventariocore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inventory API")
                        .version("1.0.0")
                        .description("API documentation for Inventory application")
                        .summary("Inventory API Documentation")
                        .termsOfService("https://swagger.io/terms/")
                        .license(new io.swagger.v3.oas.models.info.License().name("Apache 2.0").url("https://springdoc.org"))
                        .contact(new io.swagger.v3.oas.models.info.Contact().email("juandavidocampo80@gmail.com").name("Juan David Ocampo").url("https://github.com/JuDaOcVa"))
                );
    }
}
