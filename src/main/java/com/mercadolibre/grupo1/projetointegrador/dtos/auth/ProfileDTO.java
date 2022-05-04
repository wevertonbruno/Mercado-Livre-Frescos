package com.mercadolibre.grupo1.projetointegrador.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.grupo1.projetointegrador.entities.AuthenticableUser;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProfileDTO {
    @JsonProperty("ID")
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    private ProfileDTO(AuthenticableUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());
    }

    public static ProfileDTO fromUser(AuthenticableUser user){
        ProfileDTO profile = new ProfileDTO(user);
        return profile;
    }
}
