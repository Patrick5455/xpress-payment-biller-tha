
package com.xpresspayment.takehometest.commons.configs;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class OpenApiConfiguration {

    public OpenAPI openAPI(final BuildProperties buildProperties) {

        final String authentication = "Authentication";

        return new OpenAPI()
                .info(new Info()
                        .title("Revnorth - Public API")
                        .description("Service responsible for APIs on the Revnorth platform.")
                        .version(buildProperties.getVersion()))
                .addSecurityItem(new SecurityRequirement().addList(authentication))
                .components(new Components()
                        .addSecuritySchemes(authentication,
                                new SecurityScheme()
                                        .name(authentication)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));

    }
}

