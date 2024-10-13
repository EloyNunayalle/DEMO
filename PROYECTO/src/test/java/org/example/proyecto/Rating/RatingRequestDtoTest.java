package org.example.proyecto.Rating;

import org.example.proyecto.Rating.dto.RatingRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RatingRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidacionRating() {
        RatingRequestDto dto = new RatingRequestDto();
        dto.setRating(6);  // Inválido, excede el máximo permitido

        Set<ConstraintViolation<RatingRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testValidacionComentario() {
        RatingRequestDto dto = new RatingRequestDto();
        dto.setComment("Este es un comentario muy largo que excede los 500 caracteres permitidos en la validación..."); // Comentario muy largo

        Set<ConstraintViolation<RatingRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }
}
