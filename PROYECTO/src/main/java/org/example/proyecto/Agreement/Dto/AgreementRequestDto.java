package org.example.proyecto.Agreement.Dto;

import lombok.Getter;
import lombok.Setter;
import org.example.proyecto.Agreement.Domain.Agreement;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
public class AgreementRequestDto {

    @NotNull(message = "El estado no puede ser nulo")
    private Agreement.Status status;

    @NotNull(message = "La fecha de intercambio no puede ser nula")
    private LocalDateTime tradeDate;

    @NotNull(message = "El objeto ofrecido por el iniciador no puede ser nulo")
    private int itemIniId;

    @NotNull(message = "El objeto ofrecido por el receptor no puede ser nulo")
    private int itemFinId;

    @NotNull(message = "El usuario iniciador no puede ser nulo")
    private int usuarioIniId;

    @NotNull(message = "El usuario receptor no puede ser nulo")
    private int usuarioFinId;

}
