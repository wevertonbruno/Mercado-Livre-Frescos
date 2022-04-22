package com.mercadolibre.grupo1.projetointegrador;

import com.mercadolibre.grupo1.projetointegrador.config.DatabaseSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoIntegradorApplication {

    @Autowired
    private DatabaseSeeder databaseSeeder;

    public static void main(String[] args) {
        SpringApplication.run(ProjetoIntegradorApplication.class, args);
    }

    CommandLineRunner run(){
        return args -> {
            databaseSeeder.seed();
        };
    }

}
