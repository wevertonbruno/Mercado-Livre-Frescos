package com.mercadolibre.grupo1.projetointegrador.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class ObjectMapperConfig {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        objectMapper.setDateFormat(sdf);
        return objectMapper;
    }
}
