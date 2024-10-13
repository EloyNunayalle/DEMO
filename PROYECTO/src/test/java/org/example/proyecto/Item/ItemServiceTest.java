package org.example.proyecto.Item;

import org.example.proyecto.Category.Domain.Category;
import org.example.proyecto.Category.Infrastructure.CategoryRepository;
import org.example.proyecto.Item.Domain.Item;
import org.example.proyecto.Item.Domain.ItemService;
import org.example.proyecto.Item.Infraestructure.ItemRepository;
import org.example.proyecto.Item.dto.ItemRequestDto;
import org.example.proyecto.Item.dto.ItemResponseDto;
import org.example.proyecto.Usuario.Domain.Usuario;
import org.example.proyecto.Usuario.infrastructure.UsuarioRepository;
import org.example.proyecto.auth.utils.AuthorizationUtils;
import org.example.proyecto.event.item.ItemCreatedEvent;
import org.example.proyecto.exception.ResourceNotFoundException;
import org.example.proyecto.exception.UnauthorizeOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuthorizationUtils authorizationUtils;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateItem() {
        // Datos de entrada
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setCategory_id(1L);
        requestDto.setUser_id(1L);

        Category category = new Category();
        category.setId(1L);

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Item item = new Item();
        item.setId(1L);

        Item savedItem = new Item();
        savedItem.setId(1L);

        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setId(1L);

        // Simulaciones
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(modelMapper.map(requestDto, Item.class)).thenReturn(item);
        when(itemRepository.save(item)).thenReturn(savedItem);
        when(modelMapper.map(savedItem, ItemResponseDto.class)).thenReturn(responseDto);

        // Ejecutar el método
        ItemResponseDto result = itemService.createItem(requestDto);

        // Verificaciones
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(itemRepository, times(1)).save(any(Item.class));
        verify(eventPublisher, times(1)).publishEvent(any(ItemCreatedEvent.class));
    }

    @Test
    public void testUpdateItem() {
        // Datos de entrada
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setCategory_id(1L);
        requestDto.setUser_id(1L);

        Category category = new Category();
        category.setId(1L);

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Item existingItem = new Item();
        existingItem.setId(1L);
        existingItem.setUsuario(usuario);

        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setId(1L);

        // Simulaciones
        when(itemRepository.findById(1L)).thenReturn(Optional.of(existingItem));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(authorizationUtils.isAdminOrResourceOwner(1L)).thenReturn(true);
        when(itemRepository.save(existingItem)).thenReturn(existingItem);
        when(modelMapper.map(existingItem, ItemResponseDto.class)).thenReturn(responseDto);

        // Ejecutar el metodo
        ItemResponseDto result = itemService.updateItem(1L, requestDto);

        // Verificaciones
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    public void testDeleteItem() {
        // Datos de entrada
        Item item = new Item();
        item.setId(1L);

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        item.setUsuario(usuario);

        // Simulaciones
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(authorizationUtils.isAdminOrResourceOwner(1L)).thenReturn(true);

        // Ejecutar el metodo
        itemService.deleteItem(1L);

        // Verificaciones
        verify(itemRepository, times(1)).delete(item);
    }

    @Test
    public void testGetItemById() {
        // Preparación de datos
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@example.com");

        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        Item item = new Item();
        item.setId(1L);
        item.setUsuario(usuario);
        item.setCategory(category);

        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setId(1L);
        responseDto.setUserName(usuario.getEmail());

        // Simulación de comportamiento
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(modelMapper.map(any(Item.class), eq(ItemResponseDto.class))).thenReturn(responseDto);

        // Ejecución del metodo
        ItemResponseDto result = itemService.getItemById(1L);

        // Verificación
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("test@example.com", result.getUserName());
        verify(itemRepository, times(1)).findById(1L);
    }


    @Test
    public void testGetAllItems() {
        // Preparación de datos
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("usuario@example.com");

        Item item = new Item();
        item.setId(1L);
        item.setCategory(category);
        item.setUsuario(usuario);

        List<Item> items = List.of(item);

        // Simulación de comportamiento
        when(itemRepository.findAll()).thenReturn(items);

        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setId(1L);
        responseDto.setCategoryName("Electronics");
        responseDto.setUserName("usuario@example.com");

        when(modelMapper.map(any(Item.class), eq(ItemResponseDto.class))).thenReturn(responseDto);

        // Ejecución del metodo
        List<ItemResponseDto> result = itemService.getAllItems();

        // Verificación
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Electronics", result.get(0).getCategoryName());
        assertEquals("usuario@example.com", result.get(0).getUserName());
    }


    @Test
    public void testDeleteItemUnauthorized() {
        // Datos de entrada
        Item item = new Item();
        item.setId(1L);

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        item.setUsuario(usuario);

        // Simulaciones
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(authorizationUtils.isAdminOrResourceOwner(1L)).thenReturn(false);

        // Ejecutar el método y verificación de excepción
        assertThrows(UnauthorizeOperationException.class, () -> {
            itemService.deleteItem(1L);
        });

        verify(itemRepository, never()).delete(any(Item.class));
    }
}
