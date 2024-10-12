package org.example.proyecto.Item.dto;


import lombok.Getter;
import lombok.Setter;
import org.example.proyecto.Item.Domain.Condition;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemResponseDto {
    private Long id;

    private String name;

    private String description;

    private String categoryName;

    private Condition condition;

    private String userName;

    private LocalDateTime createdAt;
}
