package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.MessageDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.auth.*;
import com.mercadolibre.grupo1.projetointegrador.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

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

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestParam(name = "token") String token){
        return ResponseEntity.ok(authService.refreshToken(token));
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileDTO> me(){
        return ResponseEntity.ok(authService.getProfile());
    }

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> registerCustomer(@Valid @RequestBody RegisterDTO registerDTO){
        ProfileDTO profile = authService.registerCustomer(registerDTO);
        return ResponseEntity.created(URI.create("/me")).body(profile);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageDTO> resetPasswordRequest(@Valid @RequestBody PasswordResetDTO resetDTO){
        authService.resetPasswordRequest(resetDTO);
        return ResponseEntity.ok(MessageDTO.of("Um E-mail de alteração de senha foi enviado para " + resetDTO.getEmail()));
    }

    @PostMapping("/reset-password/verify")
    public ResponseEntity<MessageDTO> resetPassword(
            @RequestParam(required = true, name = "token") String resetToken,
            @Valid @RequestBody ChangePasswordDTO changePassword
    ){
        authService.resetPassword(resetToken, changePassword);
        return ResponseEntity.ok(MessageDTO.of("Sua senha foi alterada com sucesso!"));
    }
}
