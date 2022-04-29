package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.entities.Agent;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Rogério Lambert
 * sevice responsável por manipular informações de representantes
 */
@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentRepository agentRepository;

    /**
     * @author Rogério Lambert
     * metodo busca representante por id, e lança exceção caso não encontre
     */

    public Agent findById(Long agentId){
        return agentRepository
                .findById(agentId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Representante com ID " + agentId + " não encontrado"));
    }
}
