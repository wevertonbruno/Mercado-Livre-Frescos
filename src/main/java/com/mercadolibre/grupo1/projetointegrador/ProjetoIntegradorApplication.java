package com.mercadolibre.grupo1.projetointegrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ProjetoIntegradorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjetoIntegradorApplication.class, args);
    }
}
