package org.example.proyecto.Usuario.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.example.proyecto.Agreement.Domain.Agreement;

import org.example.proyecto.Rating.Domain.Rating;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String firstname;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres")
    private String lastname;

    @Email(message = "El correo debe ser válido")
    @NotBlank(message = "El correo no puede estar vacío")
    @Column(unique = true)
    private String email;


    @NotNull
    @Size(min = 7, max = 15, message = "El teléfono debe tener entre 7 y 15 dígitos")
    private String phone;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 100, message = "La dirección no puede tener más de 100 caracteres")
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Rating> ratingsReceived;

    @OneToMany(mappedBy = "raterUsuario", cascade = CascadeType.ALL)
    private List<Rating> ratingsGiven;

    @OneToMany(mappedBy = "initiator")
    private List<Agreement> initiatedAgreements;

    @OneToMany(mappedBy = "recipient")
    private List<Agreement> receivedAgreements;
}
