package com.mercadolibre.grupo1.projetointegrador.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSeeder.class);

    public void seed() {
        LOGGER.info("Seeding database...");
    }
}
