package org.example.proyecto.Item.Domain;

import org.example.proyecto.Category.Domain.Category;
import org.example.proyecto.Category.Infrastructure.CategoryRepository;
import org.example.proyecto.Item.Infraestructure.ItemRepository;
import org.example.proyecto.Item.dto.ItemRequestDto;
import org.example.proyecto.Item.dto.ItemResponseDto;
import org.example.proyecto.Usuario.Domain.Usuario;
import org.example.proyecto.Usuario.infrastructure.UsuarioRepository;
import org.example.proyecto.event.item.ItemCreatedEvent;
import org.example.proyecto.auth.utils.AuthorizationUtils;
import org.example.proyecto.exception.ResourceNotFoundException;
import org.example.proyecto.exception.UnauthorizeOperationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final UsuarioRepository usuarioRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    public ItemService(ApplicationEventPublisher eventPublisher, ItemRepository itemRepository, CategoryRepository categoryRepository, UsuarioRepository usuarioRepository, AuthorizationUtils authorizationUtils) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.usuarioRepository = usuarioRepository;
        this.authorizationUtils = authorizationUtils;
        this.eventPublisher = eventPublisher;
    }

    public ItemResponseDto createItem(ItemRequestDto itemDto) {
        Category category = categoryRepository.findById(itemDto.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrado"));

        Usuario user = usuarioRepository.findById(itemDto.getUser_id())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));


        //mapeamos de itemDto a item
        Item item = modelMapper.map(itemDto, Item.class);

        // Agregamos la categoria y user que no estaban en el dto
        item.setCategory(category);
        item.setUsuario(user);

        Item savedItem = itemRepository.save(item);

        // Publicar el evento de creación de ítem
        eventPublisher.publishEvent(new ItemCreatedEvent(this, savedItem));

        //mapeamos la entidad guardada a un response y luego la retornamos
        ItemResponseDto responseDto= modelMapper.map(savedItem, ItemResponseDto.class);



        responseDto.setUserName(item.getUsuario().getEmail());
        responseDto.setCategoryName(item.getCategory().getName());

        return responseDto;
    }

    public ItemResponseDto updateItem(Long itemId, ItemRequestDto itemRequestDto) {

        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item no encontrado"));


        Category category = categoryRepository.findById(itemRequestDto.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        Usuario usuario = usuarioRepository.findById(itemRequestDto.getUser_id())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!authorizationUtils.isAdminOrResourceOwner(existingItem.getUsuario().getId())) {
            throw new UnauthorizeOperationException("You do not have permission to update this item.");
        }

        modelMapper.map(itemRequestDto, existingItem);

        // Establecer manualmente la categoría y el usuario si no están en el DTO
        existingItem.setCategory(category);
        existingItem.setUsuario(usuario);

        Item updatedItem = itemRepository.save(existingItem);

        ItemResponseDto responseDto= modelMapper.map(updatedItem, ItemResponseDto.class);

        responseDto.setUserName(updatedItem.getUsuario().getEmail());
        responseDto.setCategoryName(updatedItem.getCategory().getName());

        return responseDto;
    }

    public ItemResponseDto getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item no encontrado"));


        ItemResponseDto responseDto= modelMapper.map(item, ItemResponseDto.class);

        responseDto.setUserName(item.getUsuario().getEmail());
        responseDto.setCategoryName(item.getCategory().getName());

        return responseDto;
    }

    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        if (!authorizationUtils.isAdminOrResourceOwner(item.getUsuario().getId())) {
            throw new UnauthorizeOperationException("You do not have permission to delete this item.");
        }

        itemRepository.delete(item);
    }

    public List<ItemResponseDto> getAllItems() {
        List<Item> items = itemRepository.findAll();

        // Mapear cada Item a ItemResponseDto con atributos adicionales
        return items.stream()
                .map(item -> {
                    ItemResponseDto responseDto = modelMapper.map(item, ItemResponseDto.class);
                    responseDto.setCategoryName(item.getCategory().getName());  // Seteamos el nombre de la categoría
                    responseDto.setUserName(item.getUsuario().getEmail());  // Seteamos el nombre (o email) del usuario
                    return responseDto;
                })
                .collect(Collectors.toList());
    }

    public List<ItemResponseDto> getItemsByCategory(Long categoryId) {
        List<Item> items = itemRepository.findByCategoryId(categoryId);



        return items.stream()
                .map(item -> {
                    ItemResponseDto responseDto = modelMapper.map(item, ItemResponseDto.class);
                    responseDto.setCategoryName(item.getCategory().getName());  // Seteamos el nombre de la categoría
                    responseDto.setUserName(item.getUsuario().getEmail());  // Seteamos el nombre (o email) del usuario
                    return responseDto;
                })
                .collect(Collectors.toList());
    }

    public List<ItemResponseDto> getItemsByUser(Long userId) {
        List<Item> items = itemRepository.findByUsuarioId(userId);

        return items.stream()
                .map(item -> {
                    ItemResponseDto responseDto = modelMapper.map(item, ItemResponseDto.class);
                    responseDto.setCategoryName(item.getCategory().getName());  // Seteamos el nombre de la categoría
                    responseDto.setUserName(item.getUsuario().getEmail());  // Seteamos el nombre (o email) del usuario
                    return responseDto;
                })
                .collect(Collectors.toList());
    }

    public List<ItemResponseDto> getUserItems() {
        // Obtener el email del usuario autenticado usando Spring Security
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            throw new UnauthorizeOperationException("Usuario no autenticado");
        }

        // Buscar el usuario por su email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Obtener los ítems del usuario autenticado
        List<Item> items = itemRepository.findByUsuarioId(usuario.getId());

        // Mapear los ítems a ItemResponseDto
        return items.stream()
                .map(item -> {
                    ItemResponseDto responseDto = modelMapper.map(item, ItemResponseDto.class);
                    responseDto.setCategoryName(item.getCategory().getName());
                    responseDto.setUserName(item.getUsuario().getEmail());
                    return responseDto;
                })
                .collect(Collectors.toList());
    }

}
