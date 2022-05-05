package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.StateDTO;
import com.mercadolibre.grupo1.projetointegrador.dtos.auth.ProfileDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.*;
import com.mercadolibre.grupo1.projetointegrador.repositories.AgentRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.CustomerRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;
    private final RoleService roleService;
    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;
    private final SellerRepository sellerRepository;

    @Transactional
    public ProfileDTO assignRoleToUser(String roleName, Long userId){
        Role role = roleService.findByName(roleName);
        AuthenticableUser user = userService.findUserById(userId);
        if(user.getRoles().add(role)){
            user = userService.save(user);
            switch (roleName){
                case Role.ROLE_AGENT:
                    Agent agent = new Agent(user, null);
                    agentRepository.save(agent);
                    break;
                case Role.ROLE_CUSTOMER:
                    Customer customer = new Customer(user, null);
                    customerRepository.save(customer);
                    break;
                case Role.ROLE_SELLER:
                    Seller seller = new Seller(user);
                    sellerRepository.save(seller);
                    break;
            }
        }

        return ProfileDTO.fromUser(user);
    }

    @Transactional
    public ProfileDTO setUserState(Long userId, StateDTO stateDTO) {
        AuthenticableUser user = userService.findUserById(userId);
        user.setActive(stateDTO.getActive());
        return ProfileDTO.fromUser(userService.save(user));
    }
}
