package org.example.proyecto.Agreement.Dto;

import lombok.Getter;
import lombok.Setter;
import org.example.proyecto.Agreement.Domain.State;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AgreementRequestDto {

    @NotNull(message = "El estado no puede ser nulo")
    private State state;


    @NotNull(message = "El objeto ofrecido por el iniciador no puede ser nulo")
    private Long itemIniId;

    @NotNull(message = "El objeto ofrecido por el receptor no puede ser nulo")
    private Long itemFinId;

    @NotNull(message = "El usuario iniciador no puede ser nulo")
    private Long usuarioIniId;

    @NotNull(message = "El usuario receptor no puede ser nulo")
    private Long usuarioFinId;
}