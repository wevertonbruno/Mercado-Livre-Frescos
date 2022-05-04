package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.StateDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.auth.ProfileDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidOperationException;
import com.mercadolibre.grupo1.projetointegrador.repositories.RoleRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;
    private final RoleService roleService;

    @Transactional
    public ProfileDTO assignRoleToUser(String roleName, Long userId){
        Role role = roleService.findByName(roleName);
        AuthenticableUser user = userService.findUserById(userId);
        user.getRoles().add(role);
//        switch (roleName){
//            case Role.ROLE_AGENT:
//                Agent agent = (Agent) user;
//                user = userService.save(agent);
//            case Role.ROLE_CUSTOMER:
//                Customer customer = (Customer) user;
//                user = userService.save(customer);
//            case Role.ROLE_SELLER:
//                Seller seller = (Seller) user;
//                user = userService.save(seller);
//        }

        return ProfileDTO.fromUser(userService.save(user));
    }

    @Transactional
    public ProfileDTO setUserState(Long userId, StateDTO stateDTO) {
        AuthenticableUser user = userService.findUserById(userId);
        user.setActive(stateDTO.getActive());
        return ProfileDTO.fromUser(userService.save(user));
    }
}
