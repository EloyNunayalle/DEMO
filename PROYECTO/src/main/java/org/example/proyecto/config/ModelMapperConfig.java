package org.example.proyecto.config;

import org.example.proyecto.Item.Domain.Item;
import org.example.proyecto.Item.dto.ItemResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Mantener la configuración existente
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        // Agregar la configuración para mapear userName de Item a ItemResponseDto
        modelMapper.typeMap(Item.class, ItemResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsuario().getEmail(), ItemResponseDto::setUserName);
        });

        return modelMapper;
    }
}