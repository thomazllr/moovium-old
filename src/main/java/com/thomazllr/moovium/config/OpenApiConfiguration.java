package com.thomazllr.moovium.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Moovium API", version = "v1",
        contact = @Contact(name = "Guilherme Thomaz", email = "thomaz.brito@uft.edu.br")))
public class OpenApiConfiguration {
}
