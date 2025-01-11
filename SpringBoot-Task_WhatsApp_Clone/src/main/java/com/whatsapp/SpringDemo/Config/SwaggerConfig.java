package com.whatsapp.SpringDemo.Config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("WhatsApp clone API")
                        .version("1.0.0")
                        .description("API Documentation for WhatsApp clone Api."))
                .servers(Arrays.asList(                     // added server list 
                        new Server().url("http://localhost:8080").description("local server"),
                        new Server().url("http://localhost:8081").description("test server"),
                        new Server().url("http://localhost:8082").description("new test server")))
                
                
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // Add security requirement
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }
}
