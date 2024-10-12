package org.example.proyecto.Shipment.Domain;

import jakarta.persistence.*;
import org.example.proyecto.Agreement.Domain.Agreement;

import java.time.LocalDateTime;

@Entity
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "agreement_id")
    private Agreement agreement;

    private String initiatorAddress;
    private String receiveAddress;
    private LocalDateTime deliveryDate;

}
