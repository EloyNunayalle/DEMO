package org.example.proyecto.Agreement.Dto;

import lombok.Getter;
import lombok.Setter;
import org.example.proyecto.Agreement.Domain.Agreement;
import org.example.proyecto.Agreement.Domain.Status;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
public class AgreementRequestDto {

    @NotNull(message = "El estado no puede ser nulo")
    private Status status;

    @NotNull(message = "La fecha de intercambio no puede ser nula")
    @FutureOrPresent(message = "La fecha de intercambio debe ser en el presente o futuro")
    private LocalDateTime tradeDate;

    @NotNull(message = "El objeto ofrecido por el iniciador no puede ser nulo")
    private Long itemIniId;

    @NotNull(message = "El objeto ofrecido por el receptor no puede ser nulo")
    private Long itemFinId;

    @NotNull(message = "El usuario iniciador no puede ser nulo")
    private Long usuarioIniId;

    @NotNull(message = "El usuario receptor no puede ser nulo")
    private Long usuarioFinId;
}