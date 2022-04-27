package com.mercadolibre.grupo1.projetointegrador.config;

import com.mercadolibre.grupo1.projetointegrador.entities.enums.ProductCategory;
import com.mercadolibre.grupo1.projetointegrador.exceptions.ExceptionCatchStatusCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Customizando converter para ProductCategory
 * @author Weverton Bruno
 */
@Configuration
public class EnumConverterConfig implements Converter<String, ProductCategory> {
    @Override
    public ProductCategory convert(String source) {
        try {
            return ProductCategory.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ExceptionCatchStatusCategory("Categoria inválida");
        }
    }
}
