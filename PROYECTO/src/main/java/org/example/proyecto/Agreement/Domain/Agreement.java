package org.example.proyecto.Agreement.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.example.proyecto.Item.Domain.Item;
import org.example.proyecto.Shipment.Domain.Shipment;
import org.example.proyecto.Usuario.Domain.Usuario;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El estado no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "La fecha de intercambio no puede ser nula")
    private LocalDateTime tradeDate;

    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }

    @OneToOne(mappedBy = "agreement", cascade = CascadeType.ALL)
    private Shipment shipment;

    @OneToOne
    @NotNull(message = "El objeto ofrecido por el iniciador no puede ser nulo")
    private Item item_ini;

    @OneToOne
    @NotNull(message = "El objeto ofrecido por el receptor no puede ser nulo")
    private Item item_fin;

    @ManyToOne
    @NotNull(message = "El usuario iniciador no puede ser nulo")
    private Usuario usuario_ini;

    @ManyToOne
    @NotNull(message = "El usuario receptor no puede ser nulo")
    private Usuario usuario_fin;
}