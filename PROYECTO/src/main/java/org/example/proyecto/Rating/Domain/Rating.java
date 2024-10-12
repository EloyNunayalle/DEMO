package org.example.proyecto.Rating.Domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.proyecto.Usuario.Domain.Usuario;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario usuario;  // Usuario que está siendo calificado

    @ManyToOne
    @JoinColumn(name = "rater_user_id")
    private Usuario raterUsuario;  // Usuario que realiza la calificación

    private int rating;
    private String comment;
    private LocalDateTime createdAt;

}
