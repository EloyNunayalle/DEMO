package org.example.proyecto.Agreement.Dto;

import lombok.Getter;
import lombok.Setter;
import org.example.proyecto.Agreement.Domain.Agreement;

import java.time.LocalDateTime;


@Getter
@Setter
public class AgreementResponseDto {
    private int id;
    private Agreement.Status status;
    private LocalDateTime tradeDate;
    private int itemIniId;
    private int itemFinId;
    private int usuarioIniId;
    private int usuarioFinId;
}