package com.hepsijet.kargo2.JWT.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(name = "Fatih", email = "fatih.yuksek@hepsijet.com", url = "www.hepsijet.com"
        ),
        description = "Open api document for project",
                title = "OpenApi specification - FatihYüksek", version = "1.0", license = @License(
                        name = "Licence name",
                        url = "https://some.url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers ={
                @Server(
                        description = "Local ENV", url = "http://localhost:8080"
                )
            },
        security =@SecurityRequirement(name = "bearerAuth"
        )
)
@SecurityScheme(
        name = "bearerAuth", description = "JWT auth description", scheme = "bearer", type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT", in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
