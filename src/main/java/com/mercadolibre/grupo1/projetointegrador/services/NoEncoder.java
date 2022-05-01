package com.mercadolibre.grupo1.projetointegrador.services;

import org.springframework.security.crypto.password.PasswordEncoder;

public class NoEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
