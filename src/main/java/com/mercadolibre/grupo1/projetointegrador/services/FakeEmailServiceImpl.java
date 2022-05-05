package com.mercadolibre.grupo1.projetointegrador.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Profile("test")
public class FakeEmailServiceImpl implements EmailService {
    public static Map<String, String> LAST_EMAIL_SENT = new HashMap<>();

    @Override
    public void sendEmail(String to, String body) {
        String token = (body.split("\\?token="))[1];
        LAST_EMAIL_SENT.put("to", to);
        LAST_EMAIL_SENT.put("body", body);
        LAST_EMAIL_SENT.put("token", token);
    }
}
