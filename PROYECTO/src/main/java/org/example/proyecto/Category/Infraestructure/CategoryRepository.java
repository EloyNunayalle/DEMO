package org.example.proyecto.Category.Infraestructure;

import org.example.proyecto.Category.Domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
