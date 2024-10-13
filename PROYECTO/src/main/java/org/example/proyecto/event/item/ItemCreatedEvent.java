package org.example.proyecto.event.item;


import lombok.Getter;
import org.example.proyecto.Item.Domain.Item;
import org.springframework.context.ApplicationEvent;

@Getter
public class ItemCreatedEvent extends ApplicationEvent {

    private final Item item;

    public ItemCreatedEvent(Object source, Item item) {
        super(source);
        this.item = item;
    }
}
