package org.example.proyecto.event.agreement;

import lombok.Getter;
import org.example.proyecto.Agreement.Domain.Agreement;
import org.springframework.context.ApplicationEvent;

@Getter
public class AgreementRechazadoEvent extends ApplicationEvent {

    private final Agreement agreement;

    public AgreementRechazadoEvent(Object source, Agreement agreement) {
        super(source);
        this.agreement = agreement;
    }
}
