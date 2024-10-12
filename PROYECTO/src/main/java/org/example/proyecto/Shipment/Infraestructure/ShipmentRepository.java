package org.example.proyecto.Shipment.Infraestructure;

import org.example.proyecto.Shipment.Domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
