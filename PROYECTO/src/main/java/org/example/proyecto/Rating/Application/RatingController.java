package org.example.proyecto.Rating.Application;

import org.example.proyecto.Rating.Domain.RatingService;
import org.example.proyecto.Rating.dto.RatingRequestDto;
import org.example.proyecto.Rating.dto.RatingResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // Endpoint para crear una nueva calificación
    @PostMapping("/crear")
    public ResponseEntity<RatingResponseDto> crearRating(@RequestBody RatingRequestDto requestDTO) {
        RatingResponseDto nuevoRating = ratingService.crearRating(requestDTO);
        return new ResponseEntity<>(nuevoRating, HttpStatus.CREATED);
    }

    // Endpoint para listar todas las calificaciones
    @GetMapping("/listar")
    public ResponseEntity<List<RatingResponseDto>> listarRatings() {
        List<RatingResponseDto> ratings = ratingService.listarRatings();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    // Endpoint para obtener todas las calificaciones de un usuario específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RatingResponseDto>> obtenerRatingsPorUsuario(@PathVariable Long usuarioId) {
        List<RatingResponseDto> ratings = ratingService.obtenerRatingsPorUsuario(usuarioId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}