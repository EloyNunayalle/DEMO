package org.example.proyecto.Shipment.Infraestructure;

import org.example.proyecto.Shipment.Domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
