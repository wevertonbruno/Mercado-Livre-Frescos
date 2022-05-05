package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.entities.Role;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name){
        return roleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Role com nome " + name + " não existe."));
    }
}
