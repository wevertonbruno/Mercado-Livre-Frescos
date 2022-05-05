package com.mercadolibre.grupo1.projetointegrador.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class LoggerEmailServiceImpl implements EmailService{
    public static final String FROM = "no-reply@mercadolibre.com";
    @Override
    public void sendEmail(String to, String body) {
        String email =
                "\n\n" +
                "from: " + FROM + "\n" +
                "to: " + to + "\n" +
                "body: \n" + body + "\n\n";

        System.out.println(email);
    }
}
