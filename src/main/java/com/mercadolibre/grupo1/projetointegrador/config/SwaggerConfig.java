package com.mercadolibre.grupo1.projetointegrador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title ("Mercado Livre Frescos")
                .description ("API de controle de estoque e vendas de produtos frescos do mercadolivre")
                .version("1.0.0")
                .contact(new Contact("Weverton Bruno","www.linkedin.com/in/oieusouweverton", "weverton.ssantos@mercadolibre.com"))
                .build();

        return apiInfo;
    }
}
