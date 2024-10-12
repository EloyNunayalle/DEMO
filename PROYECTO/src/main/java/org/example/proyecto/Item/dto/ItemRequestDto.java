package org.example.proyecto.Item.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.proyecto.Category.Domain.Category;
import org.example.proyecto.Item.Domain.Condition;
import org.example.proyecto.Item.Domain.Item;
import org.example.proyecto.Usuario.Domain.Usuario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class ItemRequestDto {
    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Long category_id;

    @NotNull
    private Long user_id;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private Condition condition;



}
