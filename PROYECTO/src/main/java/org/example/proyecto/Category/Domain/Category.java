package org.example.proyecto.Category.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.proyecto.Item.Domain.Item;

import java.util.List;

@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;


    @OneToMany
    private List<Item> items;
}