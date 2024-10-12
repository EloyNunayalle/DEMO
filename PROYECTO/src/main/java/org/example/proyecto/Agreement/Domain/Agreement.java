    package org.example.proyecto.Agreement.Domain;
    
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    
    import org.example.proyecto.Item.Domain.Item;
    import org.example.proyecto.Shipment.Domain.Shipment;
    import org.example.proyecto.Usuario.Domain.Usuario;
    
    import javax.validation.constraints.FutureOrPresent;
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
        @FutureOrPresent(message = "La fecha de intercambio debe ser en el presente o futuro")
        private LocalDateTime tradeDate;

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
        private Usuario initiator;
    
        @ManyToOne
        @NotNull(message = "El usuario receptor no puede ser nulo")
        private Usuario recipient;

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