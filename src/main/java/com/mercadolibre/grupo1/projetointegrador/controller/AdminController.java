package com.mercadolibre.grupo1.projetointegrador.controller;

import com.mercadolibre.grupo1.projetointegrador.dtos.StateDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.auth.ProfileDTO;
import com.mercadolibre.grupo1.projetointegrador.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/")
public class AdminController {
    private final AdminService adminService;

    @PutMapping("users/{userId}/assign/{roleName}")
    public ResponseEntity<ProfileDTO> assignUserRole(
            @PathVariable Long userId,
            @PathVariable @Pattern(regexp = "(^ROLE_ADMIN$|^ROLE_CUSTOMER$|^ROLE_AGENT$|^ROLE_SELLER$)", message = "O cargo ${validatedValue} não é aceito!") String roleName){
        ProfileDTO dto = adminService.assignRoleToUser(roleName, userId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/users/{userId}/enabled")
    public ResponseEntity<ProfileDTO> enableUser(@PathVariable Long userId, @RequestBody StateDTO stateDTO ){
        ProfileDTO dto = adminService.setUserState(userId, stateDTO);
        return ResponseEntity.ok(dto);
    }
}
