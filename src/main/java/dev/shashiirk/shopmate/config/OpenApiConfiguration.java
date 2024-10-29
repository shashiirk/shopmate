package dev.shashiirk.shopmate.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                              .title("Shopmate API")
                              .version("0.0.1")
                              .description(
                                      "API documentation for Shopmate e-commerce store, providing APIs for managing products, orders, users, etc."
                              )
                              .contact(new Contact()
                                               .name("Shashikanth Reddy")
                                               .email("kolanshashii@gmail.com")
                              )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                                    .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                            .type(SecurityScheme.Type.HTTP)
                                            .scheme("bearer")
                                            .bearerFormat("JWT")
                                    )
                );
    }
}
