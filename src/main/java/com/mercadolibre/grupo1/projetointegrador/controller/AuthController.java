package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.MessageDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.auth.*;
import com.mercadolibre.grupo1.projetointegrador.services.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/v1")
@Api(tags = "Authentication Controller")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation("U.S 01 - Autenticacao de usuário")
    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO credentials){
        return ResponseEntity.ok(authService.login(credentials));
    }

    @ApiOperation("U.S 06 - Refresh Token")
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestParam(name = "token") String token){
        return ResponseEntity.ok(authService.refreshToken(token));
    }

    @ApiOperation("U.S 06 - Get User Authenticated Profile")
    @GetMapping("/me")
    public ResponseEntity<ProfileDTO> me(){
        return ResponseEntity.ok(authService.getProfile());
    }

    @ApiOperation("U.S 06 - Register User")
    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> registerCustomer(@Valid @RequestBody RegisterDTO registerDTO){
        ProfileDTO profile = authService.registerCustomer(registerDTO);
        return ResponseEntity.created(URI.create("/me")).body(profile);
    }

    @ApiOperation("U.S 06 - Reset Password Request")
    @PostMapping("/reset-password")
    public ResponseEntity<MessageDTO> resetPasswordRequest(@Valid @RequestBody PasswordResetDTO resetDTO){
        authService.resetPasswordRequest(resetDTO);
        return ResponseEntity.ok(MessageDTO.of("Um E-mail de alteração de senha foi enviado para " + resetDTO.getEmail()));
    }

    @ApiOperation("U.S 06 - Reset Password Token Verify")
    @PostMapping("/reset-password/verify")
    public ResponseEntity<MessageDTO> resetPassword(
            @RequestParam(required = true, name = "token") String resetToken,
            @Valid @RequestBody ChangePasswordDTO changePassword
    ){
        authService.resetPassword(resetToken, changePassword);
        return ResponseEntity.ok(MessageDTO.of("Sua senha foi alterada com sucesso!"));
    }
}
