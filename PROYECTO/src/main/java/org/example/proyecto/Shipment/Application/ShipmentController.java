package org.example.proyecto.Shipment.Application;
import org.example.proyecto.Agreement.Domain.Agreement;
import org.example.proyecto.Agreement.Infrastructure.AgreementRepository;
import org.example.proyecto.Category.Domain.Category;
import org.example.proyecto.Item.Domain.Item;
import org.example.proyecto.Item.dto.ItemRequestDto;
import org.example.proyecto.Item.dto.ItemResponseDto;
import org.example.proyecto.Shipment.Domain.ShipmentService;
import org.example.proyecto.Shipment.Dto.ShipmentRequestDto;
import org.example.proyecto.Shipment.Dto.ShipmentResponseDto;
import org.example.proyecto.Usuario.Domain.Usuario;
import org.example.proyecto.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private AgreementRepository agreementRepository;

    // Obtener todos los envíos
    @GetMapping
    public List<ShipmentResponseDto> getAllShipments() {
        return shipmentService.getAllShipments();
    }

    // Crear un nuevo envío
    @PostMapping
    public ShipmentResponseDto createShipmentForAgreement(@Valid @RequestBody ShipmentRequestDto shipmentRequestDto) {
        Agreement agreement = agreementRepository.findById(shipmentRequestDto.getAgreementId())
                .orElseThrow(() -> new ResourceNotFoundException("Agreement not found"));

        shipmentService.createShipmentForAgreement(agreement);

        return new ShipmentResponseDto();
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