package com.mercadolibre.grupo1.projetointegrador.services;

import org.springframework.stereotype.Service;

public interface EmailService {
    void sendEmail(String to, String body);
}
