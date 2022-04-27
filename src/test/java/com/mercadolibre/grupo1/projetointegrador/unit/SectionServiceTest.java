package com.mercadolibre.grupo1.projetointegrador.unit;

import com.mercadolibre.grupo1.projetointegrador.dtos.SectionDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.Section;
import com.mercadolibre.grupo1.projetointegrador.entities.Warehouse;
import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.repositories.SectionRepository;
import com.mercadolibre.grupo1.projetointegrador.services.SectionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SectionServiceTest {

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private SectionService sectionService;

    @Test
    public void itShouldReturnASectionNotFoundException(){
        SectionDTO sectionDTO = createFakeSectionDTO();
        when(sectionRepository.findByIdAndWarehouse_Id(anyLong(), anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            sectionService.findBySectionDto(sectionDTO);
        });

        assertEquals("Sessão e/ou Armazem não encontrado na base de dados", exception.getMessage());
    }

    private SectionDTO createFakeSectionDTO(){
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setSectionCode(1L);
        sectionDTO.setWarehouseCode(1L);
        return sectionDTO;
    }

}