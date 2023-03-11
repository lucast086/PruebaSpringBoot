package com.example.PruebaSpringBoot.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** configuracion swagger para la generacion de documentacion de api rest
 *
 */
@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    //Docket es un builder(patron builder) esta pensado para ser
    //la interfaz principal al framework de springfox
    @Bean
    public OpenAPI api(){
        return new OpenAPI().info(new Info()
                .title("Spring OpenAPI")
                .version("1.0")
                .description("Spring doc"));
    }
}
