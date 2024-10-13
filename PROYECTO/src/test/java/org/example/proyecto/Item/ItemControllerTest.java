package org.example.proyecto.Item;

import org.example.proyecto.Item.Application.ItemController;
import org.example.proyecto.Item.Domain.ItemService;
import org.example.proyecto.Item.dto.ItemRequestDto;
import org.example.proyecto.Item.dto.ItemResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateItem() {
        // Preparar datos
        ItemRequestDto requestDto = new ItemRequestDto();
        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setId(1L);

        // Simular comportamiento del servicio
        when(itemService.createItem(requestDto)).thenReturn(responseDto);

        // Ejecutar el controlador
        ResponseEntity<ItemResponseDto> response = itemController.createItem(requestDto);

        // Verificaciones
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testApproveItem() {
        // Preparar datos
        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setId(1L);

        // Simular comportamiento del servicio
        when(itemService.approveItem(1L, true)).thenReturn(responseDto);

        // Ejecutar el controlador
        ResponseEntity<ItemResponseDto> response = itemController.approveItem(1L, true);

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testUpdateItem() {
        // Preparar datos
        ItemRequestDto requestDto = new ItemRequestDto();
        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setId(1L);

        // Simular comportamiento del servicio
        when(itemService.updateItem(1L, requestDto)).thenReturn(responseDto);

        // Ejecutar el controlador
        ResponseEntity<ItemResponseDto> response = itemController.updateItem(1L, requestDto);

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testGetItem() {
        // Preparar datos
        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setId(1L);

        // Simular comportamiento del servicio
        when(itemService.getItemById(1L)).thenReturn(responseDto);

        // Ejecutar el controlador
        ResponseEntity<ItemResponseDto> response = itemController.getItem(1L);

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testGetAllItems() {
        // Preparar datos
        ItemResponseDto item1 = new ItemResponseDto();
        item1.setId(1L);
        ItemResponseDto item2 = new ItemResponseDto();
        item2.setId(2L);

        List<ItemResponseDto> items = Arrays.asList(item1, item2);

        // Simular comportamiento del servicio
        when(itemService.getAllItems()).thenReturn(items);

        // Ejecutar el controlador
        ResponseEntity<List<ItemResponseDto>> response = itemController.getAllItems();

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testDeleteItem() {
        // Simular comportamiento del servicio
        doNothing().when(itemService).deleteItem(1L);

        // Ejecutar el controlador
        ResponseEntity<ItemResponseDto> response = itemController.deleteItem(1L);

        // Verificaciones
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(itemService, times(1)).deleteItem(1L);
    }

    @Test
    public void testGetItemsByCategory() {
        // Preparar datos
        ItemResponseDto item1 = new ItemResponseDto();
        item1.setId(1L);
        ItemResponseDto item2 = new ItemResponseDto();
        item2.setId(2L);

        List<ItemResponseDto> items = Arrays.asList(item1, item2);

        // Simular comportamiento del servicio
        when(itemService.getItemsByCategory(1L)).thenReturn(items);

        // Ejecutar el controlador
        ResponseEntity<List<ItemResponseDto>> response = itemController.getItemByCategory(1L);

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetItemsByUser() {
        // Preparar datos
        ItemResponseDto item1 = new ItemResponseDto();
        item1.setId(1L);
        ItemResponseDto item2 = new ItemResponseDto();
        item2.setId(2L);

        List<ItemResponseDto> items = Arrays.asList(item1, item2);

        // Simular comportamiento del servicio
        when(itemService.getItemsByUser(1L)).thenReturn(items);

        // Ejecutar el controlador
        ResponseEntity<List<ItemResponseDto>> response = itemController.getItemByUserId(1L);

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetUserItems() {
        // Preparar datos
        ItemResponseDto item1 = new ItemResponseDto();
        item1.setId(1L);
        ItemResponseDto item2 = new ItemResponseDto();
        item2.setId(2L);

        List<ItemResponseDto> items = Arrays.asList(item1, item2);

        // Simular comportamiento del servicio
        when(itemService.getUserItems()).thenReturn(items);

        // Ejecutar el controlador
        ResponseEntity<List<ItemResponseDto>> response = itemController.getUserItems();

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}
