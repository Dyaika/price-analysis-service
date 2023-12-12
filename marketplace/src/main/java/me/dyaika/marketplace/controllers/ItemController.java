package me.dyaika.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.dyaika.marketplace.entities.Item;
import me.dyaika.marketplace.responses.GetItemResponse;
import me.dyaika.marketplace.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Товары.", description = "Просмотр информации о товарах, наличии и ценах.")
@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService; // Предполагаем, что у вас есть сервис для работы с товарами

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation("Просмотр информации о товаре.")
    @GetMapping("/{item_id}")
    public ResponseEntity<GetItemResponse> getItem(@PathVariable Long item_id) {
        Item item = itemService.getItemById(item_id);

        if (item != null) {
            // Создаем объект GetItemResponse на основе данных о товаре
            GetItemResponse response = new GetItemResponse(
                    item.getItemId(),
                    item.getItemName(),
                    item.getItemDescription(),
                    item.getCategory().getCategoryName()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
