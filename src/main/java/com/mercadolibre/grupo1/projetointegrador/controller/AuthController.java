package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.LoginDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.TokenDTO;
import com.mercadolibre.grupo1.projetointegrador.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO credentials){
        return ResponseEntity.ok(authService.login(credentials));
    }
}
