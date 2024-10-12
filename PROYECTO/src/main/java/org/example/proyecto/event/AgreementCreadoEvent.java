package org.example.proyecto.event;

import lombok.Getter;
import org.example.proyecto.Agreement.Domain.Agreement;
import org.springframework.context.ApplicationEvent;

@Getter
public class AgreementCreadoEvent extends ApplicationEvent {

    private final Agreement agreement;

    public AgreementCreadoEvent(Object source, Agreement agreement) {
        super(source);
        this.agreement = agreement;
    }
}