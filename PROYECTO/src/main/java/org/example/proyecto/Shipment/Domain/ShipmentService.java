package org.example.proyecto.Shipment.Domain;

import org.example.proyecto.Agreement.Domain.Agreement;
import org.example.proyecto.Agreement.Infraestructure.AgreementRepository;
import org.example.proyecto.Shipment.Dto.ShipmentRequestDto;
import org.example.proyecto.Shipment.Dto.ShipmentResponseDto;
import org.example.proyecto.Shipment.Infraestructure.ShipmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private AgreementRepository agreementRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ShipmentResponseDto> getAllShipments() {
        return shipmentRepository.findAll().stream()
                .map(shipment -> modelMapper.map(shipment, ShipmentResponseDto.class))
                .collect(Collectors.toList());
    }

    public ShipmentResponseDto createShipment(ShipmentRequestDto shipmentRequestDto) {
        Shipment shipment = new Shipment();
        mapRequestDtoToShipment(shipmentRequestDto, shipment);
        Shipment savedShipment = shipmentRepository.save(shipment);
        return modelMapper.map(savedShipment, ShipmentResponseDto.class);
    }

    public ShipmentResponseDto getShipmentById(Long id) {  // Cambiado de int a Long
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
        return modelMapper.map(shipment, ShipmentResponseDto.class);
    }

    public ShipmentResponseDto updateShipment(Long id, ShipmentRequestDto shipmentRequestDto) {  // Cambiado de int a Long
        Shipment existingShipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
        mapRequestDtoToShipment(shipmentRequestDto, existingShipment);
        Shipment updatedShipment = shipmentRepository.save(existingShipment);
        return modelMapper.map(updatedShipment, ShipmentResponseDto.class);
    }

    public void deleteShipment(Long id) {  // Cambiado de int a Long
        shipmentRepository.deleteById(id);
    }

    private void mapRequestDtoToShipment(ShipmentRequestDto dto, Shipment shipment) {
        shipment.setInitiatorAddress(dto.getInitiatorAddress());
        shipment.setReceiveAddress(dto.getReceiveAddress());
        shipment.setDeliveryDate(dto.getDeliveryDate());

        Agreement agreement = agreementRepository.findById(dto.getAgreementId())
                .orElseThrow(() -> new RuntimeException("Agreement not found"));
        shipment.setAgreement(agreement);
    }
}