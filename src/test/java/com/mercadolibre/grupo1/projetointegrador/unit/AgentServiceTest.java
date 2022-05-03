package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.entities.Agent;
import com.mercadolibre.grupo1.projetointegrador.entities.AuthenticableUser;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.AgentRepository;
import com.mercadolibre.grupo1.projetointegrador.services.AgentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgentServiceTest {
    @Mock
    private AgentRepository agentRepository;

    @InjectMocks
    private AgentService agentService;

    /**
     * @author Rogério Lambert
     * Testes unitarios do service de gestão do represante da warehouse
     */

    @Test
    @DisplayName("Testa se a query certa é chamada quando o método findById é chamado retornando um objeto Agent: ")
    public void itShouldCallFindById() {
        //setup do test
        AuthenticableUser user = AuthenticableUser.builder().id(3L).build();
        Agent agent = new Agent(user, null);
        when(agentRepository.findById(1L)).thenReturn(Optional.of(agent));

        //execução
        Agent agentReturned = agentService.findById(1L);

        //verificação
        assertEquals(agent.getId(), agentReturned.getId());
    }

    @Test
    @DisplayName("Testa se uma exceção correta é lançada quando o agente não é encontrado: ")
    public void itShouldThrowNotFoundEntity() {
        //setup do test
        when(agentRepository.findById(1L)).thenReturn(Optional.empty());

        //execução
        Exception e = assertThrows(EntityNotFoundException.class, () -> agentService.findById(1L));

        //verificação
        assertEquals(e.getMessage(), "Representante com ID 1 não encontrado");
    }
}
