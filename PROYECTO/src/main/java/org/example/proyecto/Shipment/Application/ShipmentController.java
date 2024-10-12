package org.example.proyecto.Shipment.Application;

import org.example.proyecto.Shipment.Domain.ShipmentService;
import org.example.proyecto.Shipment.Dto.ShipmentRequestDto;
import org.example.proyecto.Shipment.Dto.ShipmentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    // Obtener todos los envíos
    @GetMapping
    public List<ShipmentResponseDto> getAllShipments() {
        return shipmentService.getAllShipments();
    }

    // Crear un nuevo envío
    @PostMapping
    public ShipmentResponseDto createShipment(@Valid @RequestBody ShipmentRequestDto shipmentRequestDto) {
        return shipmentService.createShipment(shipmentRequestDto);
    }

    // Obtener un envío por ID
    @GetMapping("/{id}")
    public ShipmentResponseDto getShipmentById(@PathVariable Long id) {
        return shipmentService.getShipmentById(id);
    }

    // Actualizar un envío por ID
    @PutMapping("/{id}")
    public ShipmentResponseDto updateShipment(@PathVariable Long id, @Valid @RequestBody ShipmentRequestDto shipmentRequestDto) {
        return shipmentService.updateShipment(id, shipmentRequestDto);
    }

    // Eliminar un envío por ID
    @DeleteMapping("/{id}")
    public void deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
    }
}
