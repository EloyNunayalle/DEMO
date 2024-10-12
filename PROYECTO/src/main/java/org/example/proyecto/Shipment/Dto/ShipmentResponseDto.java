package org.example.proyecto.Shipment.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShipmentResponseDto {

    private Long id;
    private String initiatorAddress;
    private String receiveAddress;
    private LocalDateTime deliveryDate;
    private Long agreementId;
}
