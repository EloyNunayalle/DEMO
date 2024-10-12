package org.example.proyecto.Rating.Infraestructure;

import org.example.proyecto.Rating.Domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
