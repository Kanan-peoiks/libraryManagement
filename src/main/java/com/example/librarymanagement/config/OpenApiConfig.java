package com.example.librarymanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Library Management System API",
                version = "v1.0",
                description = "Kitabxana İdarəetmə Sistemi RESTful API Sənədləşdirilməsi (Keşləmə, Fayl yükləmə, Asinxron bildirişlər və Scheduled tapşırıqlar dəstəyi ilə)",
                contact = @Contact(name = "Developer Team", email = "info@library.com")
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "JWT Token daxil edin"
)
public class OpenApiConfig {
}