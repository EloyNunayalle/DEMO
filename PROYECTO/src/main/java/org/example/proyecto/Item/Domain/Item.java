package org.example.proyecto.Item.Domain;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.example.proyecto.Category.Domain.Category;
import org.example.proyecto.Usuario.Domain.Usuario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String name;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;

    @NotNull(message = "La categoría no puede ser nula")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @Enumerated(EnumType.STRING)
    @NotNull(message = "La condición del ítem no puede ser nula")
    private Condition condition;

    @Enumerated(EnumType.STRING)
    private Status status; // estado de item (Pendiente, aprobado, denegado

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "El usuario no puede ser nulo")
    private Usuario usuario;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}