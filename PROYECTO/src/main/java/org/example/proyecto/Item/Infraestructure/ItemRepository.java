package org.example.proyecto.Item.Infraestructure;

import org.example.proyecto.Item.Domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategoryId(Long category_id);
    List<Item> findByUsuarioId(Long user_id);
}
