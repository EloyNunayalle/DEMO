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
    private Long id;

    private int rating;

    private String comment;

    // El usuario que da la calificación
    @ManyToOne
    @JoinColumn(name = "rater_usuario_id")
    private Usuario raterUsuario;

    // El usuario que recibe la calificación
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
