package me.dyaika.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.dyaika.marketplace.entities.Item;
import me.dyaika.marketplace.dto.requests.CreateItemRequest;
import me.dyaika.marketplace.dto.requests.UpdateItemRequest;
import me.dyaika.marketplace.dto.responses.GetItemResponse;
import me.dyaika.marketplace.dto.responses.GetNiceItemResponse;
import me.dyaika.marketplace.security.RoleChecker;
import me.dyaika.marketplace.services.ItemService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "Товары.", description = "Просмотр информации о товарах.")
@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final RoleChecker roleChecker;

    public ItemController(ItemService itemService, RoleChecker roleChecker) {
        this.itemService = itemService;
        this.roleChecker = roleChecker;
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
    public ResponseEntity<Long> deleteItem(@PathVariable Long item_id, HttpServletRequest request) {
        if (roleChecker.checkRole("ADMIN", request)){
            try {
                itemService.deleteItem(item_id);
                return new ResponseEntity<>(item_id, HttpStatus.OK);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @ApiOperation("Создание нового товара.")
    @PostMapping("/create")
    public ResponseEntity<Long> createItem(@RequestBody CreateItemRequest newItem, HttpServletRequest request) {
        if (roleChecker.checkRole("ADMIN", request)){
            return new ResponseEntity<>(itemService.createItem(newItem), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation("Обновление информации о товаре.")
    @PutMapping("/update/{item_id}")
    public ResponseEntity<GetItemResponse> updateItem(@PathVariable Long item_id, @RequestBody UpdateItemRequest updatedItem, HttpServletRequest request) {
        if (roleChecker.checkRole("ADMIN", request)){
            return getItem(itemService.updateItem(item_id, updatedItem));
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
