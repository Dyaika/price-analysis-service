package me.dyaika.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.dyaika.marketplace.entities.Item;
import me.dyaika.marketplace.entities.ItemParameter;
import me.dyaika.marketplace.requests.CreateItemRequest;
import me.dyaika.marketplace.requests.UpdateItemRequest;
import me.dyaika.marketplace.responses.GetItemResponse;
import me.dyaika.marketplace.responses.GetNiceItemResponse;
import me.dyaika.marketplace.services.ItemService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Товары.", description = "Просмотр информации о товарах, наличии и ценах.")
@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService; // Предполагаем, что у вас есть сервис для работы с товарами

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation("Просмотр информации о товаре красиво.")
    @GetMapping("/{item_id}/nice")
    public ResponseEntity<GetNiceItemResponse> getNiceItem(@PathVariable Long item_id) {
        GetNiceItemResponse response = itemService.getNiceItem(item_id);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Просмотр информации о товаре как в базе.")
    @GetMapping("/{item_id}")
    public ResponseEntity<GetItemResponse> getItem(@PathVariable Long item_id) {
        Item item = itemService.getItemById(item_id);

        if (item != null) {
            // Создаем объект GetItemResponse на основе данных о товаре
            GetItemResponse response = new GetItemResponse(
                    item.getItemId(),
                    item.getItemName(),
                    item.getItemDescription(),
                    item.getCategory().getCategoryId()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Просмотр списка всех товаров.")
    @GetMapping("/all")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @ApiOperation("Удаление товара.")
    @DeleteMapping("/{item_id}")
    public ResponseEntity<Long> deleteItem(@PathVariable Long item_id) {
        try {
            itemService.deleteItem(item_id);
            return new ResponseEntity<>(item_id, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @ApiOperation("Создание нового товара.")
    @PostMapping("/create")
    public Long createItem(@RequestBody CreateItemRequest newItem) {
        return itemService.createItem(newItem);
    }

    @ApiOperation("Обновление информации о товаре.")
    @PutMapping("/update/{item_id}")
    public ResponseEntity<GetItemResponse> updateItem(@PathVariable Long item_id, @RequestBody UpdateItemRequest updatedItem) {
        return getItem(itemService.updateItem(item_id, updatedItem));
    }
}
