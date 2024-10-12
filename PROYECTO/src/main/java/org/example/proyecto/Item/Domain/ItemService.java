package org.example.proyecto.Item.Domain;

import org.example.proyecto.Category.Domain.Category;
import org.example.proyecto.Category.Infraestructure.CategoryRepository;
import org.example.proyecto.Item.Infraestructure.ItemRepository;
import org.example.proyecto.Item.dto.ItemRequestDto;
import org.example.proyecto.Item.dto.ItemResponseDto;
import org.example.proyecto.Usuario.Domain.Usuario;
import org.example.proyecto.Usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository, UsuarioRepository usuarioRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.usuarioRepository = usuarioRepository;
    }

   public ItemResponseDto createItem(ItemRequestDto itemDto) {
       Category category = categoryRepository.findById(itemDto.getCategory_id())
               .orElseThrow(() -> new RuntimeException("Categoria no encontrado"));

       Usuario user = usuarioRepository.findById(itemDto.getUser_id())
               .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

       //mapeamos de itemDto a item
       Item item = modelMapper.map(itemDto, Item.class);

       // Agregamos la categoria y user que no estaban en el dto
       item.setCategory(category);
       item.setUsuario(user);

       Item savedItem = itemRepository.save(item);
       ItemResponseDto responseDto = modelMapper.map(savedItem, ItemResponseDto.class);
       responseDto.setCategoryName(savedItem.getCategory().getName());
       responseDto.setUserName(savedItem.getUsuario().getEmail());
       //mapeamos la entidad guardada a un response y luego la retornamos
       return responseDto;
   }

    public ItemResponseDto updateItem(Long itemId, ItemRequestDto itemRequestDto) {

        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));


        Category category = categoryRepository.findById(itemRequestDto.getCategory_id())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Usuario usuario = usuarioRepository.findById(itemRequestDto.getUser_id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        modelMapper.map(itemRequestDto, existingItem);

        // Establecer manualmente la categoría y el usuario si no están en el DTO
        existingItem.setCategory(category);
        existingItem.setUsuario(usuario);

        Item updatedItem = itemRepository.save(existingItem);

        ItemResponseDto responseDto = modelMapper.map(updatedItem, ItemResponseDto.class);
        responseDto.setCategoryName(updatedItem.getCategory().getName());
        responseDto.setUserName(updatedItem.getUsuario().getEmail());

        return responseDto;
    }

    public ItemResponseDto getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        ItemResponseDto responseDto = modelMapper.map(item, ItemResponseDto.class);
        responseDto.setCategoryName(item.getCategory().getName());
        responseDto.setUserName(item.getUsuario().getEmail());
        return responseDto;
    }

    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));
        itemRepository.delete(item);
    }

    public List<ItemResponseDto> getAllItems() {
        List<Item> items = itemRepository.findAll();

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
}
