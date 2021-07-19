package com.meli.br.imoveis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.meli.br.imoveis"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.POST, this.getListPossibleErrors());
    }

    private List<Response> getListPossibleErrors() {
        return List.of(
                new ResponseBuilder().code("400").description("Bad request").build(),
                new ResponseBuilder().code("404").description("Not Found").build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API Imóveis")
                .description("Desafio Quality - Grupo 1 Wave 02 - Brasil - API Rest Imoveis com cobertura de testes")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }

}