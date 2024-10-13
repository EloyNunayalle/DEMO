package org.example.proyecto.Rating.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RatingRequestDto {

    @NotNull(message = "El ID del usuario que recibe la calificación es requerido")
    private Long usuarioId;

    @NotNull(message = "El ID del usuario que da la calificación es requerido")
    private Long raterUsuarioId;
    @Min(1)
    @Max(5)
    @NotNull(message = "La calificación es requerida y debe estar entre 1 y 5")
    private int rating;

    @NotBlank(message = "El comentario no puede estar vacío")
    private String comment;
}