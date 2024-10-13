package org.example.proyecto.Item.Application;

import org.example.proyecto.Item.Domain.ItemService;
import org.example.proyecto.Item.dto.ItemRequestDto;
import org.example.proyecto.Item.dto.ItemResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody ItemRequestDto requestDto) {
        ItemResponseDto responseDto = itemService.createItem(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/{itemId}/approve")
    public ResponseEntity<ItemResponseDto> approveItem(@PathVariable Long itemId, @RequestParam boolean approve) {
        ItemResponseDto responseDto = itemService.approveItem(itemId, approve);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> updateItem(@PathVariable Long itemId, @RequestBody ItemRequestDto requestDto) {
        ItemResponseDto updateItem = itemService.updateItem(itemId, requestDto);
        return ResponseEntity.ok(updateItem);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> getItem(@PathVariable Long itemId) {
        ItemResponseDto item = itemService.getItemById(itemId);
        return ResponseEntity.ok(item);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ItemResponseDto>> getItemByCategory(@PathVariable Long categoryId) {
        List<ItemResponseDto> items = itemService.getItemsByCategory(categoryId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ItemResponseDto>> getItemByUserId(@PathVariable Long userId) {
        List<ItemResponseDto> items = itemService.getItemsByUser(userId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/items/mine")
    public ResponseEntity<List<ItemResponseDto>> getUserItems() {
        List<ItemResponseDto> items = itemService.getUserItems();
        return ResponseEntity.ok(items);
    }
}
