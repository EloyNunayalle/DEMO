package org.example.proyecto.event;

import lombok.Getter;
import org.example.proyecto.Usuario.Domain.Usuario;
import org.springframework.context.ApplicationEvent;

@Getter
public class UsuarioCreadoEvent extends ApplicationEvent {

    private final Usuario usuario;

    public UsuarioCreadoEvent(Object source, Usuario usuario) {
        super(source);
        this.usuario = usuario;
    }
}