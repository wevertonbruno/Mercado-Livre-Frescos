package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.entities.Agent;
import com.mercadolibre.grupo1.projetointegrador.entities.Role;
import com.mercadolibre.grupo1.projetointegrador.entities.Seller;
import com.mercadolibre.grupo1.projetointegrador.repositories.AgentRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.RoleRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSeeder.class);

    private final SellerRepository sellerRepository;
    private final AgentRepository agentRepository;
    private final RoleRepository roleRepository;

    public void seed() {
        LOGGER.info("Seeding database...");

        seedRoles();
        seedSellers();
        seedAgents();

        LOGGER.info("Seeding complete...");
    }

    private void seedRoles(){
        roleRepository.save(Role.builder().id(1L).name("ROLE_AGENT").build());
        roleRepository.save(Role.builder().id(2L).name("ROLE_SELLER").build());
    }

    private void seedSellers(){
        Role sellerRole = roleRepository.findById(2L).get();
        sellerRepository.save(Seller.builder().id(1L).username("seller1").password("123456").email("seller1@mercadolibre.com").roles(Set.of(sellerRole)).build());
        sellerRepository.save(Seller.builder().id(2L).username("seller2").password("123456").email("seller2@mercadolibre.com").roles(Set.of(sellerRole)).build());
    }

    private void seedAgents(){
        Role agentRole = roleRepository.findById(1L).get();
        agentRepository.save(Agent.builder().id(1L).username("agent1").password("123456").email("agent1@mercadolibre.com").roles(Set.of(agentRole)).build());
        agentRepository.save(Agent.builder().id(2L).username("agent2").password("123456").email("agent2@mercadolibre.com").roles(Set.of(agentRole)).build());
    }
}
