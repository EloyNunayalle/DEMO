package org.example.proyecto.Shipment.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShipmentResponseDto {

    private int id;
    private String initiatorAddress;
    private String receiveAddress;
    private LocalDateTime deliveryDate;
    private int agreementId;
}
