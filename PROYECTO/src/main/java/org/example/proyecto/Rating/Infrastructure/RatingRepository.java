package org.example.proyecto.Rating.Infrastructure;

import org.example.proyecto.Rating.Domain.Rating;
import org.example.proyecto.Usuario.Domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByUsuario(Usuario usuario);
}
