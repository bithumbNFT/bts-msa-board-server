package com.bts.api.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bts.api.board.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Board Server Test API")
                .version("0.0.1")
                .description("API for basic CRUD for Sample Test")
                .contact(new Contact("Seungjae Moon", "github.com/msj0319", "gaeddongie13@gmail.com"))
//                .license("Apache License Version 2.0")
                .build();
    }
}
