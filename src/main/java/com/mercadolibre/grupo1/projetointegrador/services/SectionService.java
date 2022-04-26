package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.SectionDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.BatchStock;
import com.mercadolibre.grupo1.projetointegrador.entities.InboundOrder;
import com.mercadolibre.grupo1.projetointegrador.entities.Section;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.InboundOrderRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SectionService {
    private final SectionRepository sectionRepository;

    public Section findBySectionDto(SectionDTO sectionDto) {
        return sectionRepository.findByIdAndWarehouse_Id(sectionDto.getSectionCode(), sectionDto.getWarehouseCode())
                .orElseThrow(() -> new EntityNotFoundException("Sessão e/ou Armazem não encontrado na base de dados"));
    }
}
