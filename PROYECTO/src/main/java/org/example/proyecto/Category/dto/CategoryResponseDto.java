package org.example.proyecto.Category.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.proyecto.Item.dto.ItemResponseDto;

import java.util.List;

@Getter
@Setter
public class CategoryResponseDto {
    private Long id;

    private String name;

    private String description;

    private List<ItemResponseDto> items;
}
