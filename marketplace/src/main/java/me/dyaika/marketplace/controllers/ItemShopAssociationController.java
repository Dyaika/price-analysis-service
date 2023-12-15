package me.dyaika.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.dyaika.marketplace.dto.AssociatedItem;
import me.dyaika.marketplace.dto.responses.GetShopAssociationsResponse;
import me.dyaika.marketplace.entities.ItemShopAssociation;
import me.dyaika.marketplace.services.ItemShopAssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Ассоциация товар-магазин.", description = "Управление информацией о товарах в магазине.")
@RestController
@RequestMapping("/item-shop-associations")
public class ItemShopAssociationController {

    private final ItemShopAssociationService itemShopAssociationService;

    @Autowired
    public ItemShopAssociationController(ItemShopAssociationService itemShopAssociationService) {
        this.itemShopAssociationService = itemShopAssociationService;
    }

    @ApiOperation("Добавление товара в магазин.")
    @PostMapping
    public ResponseEntity<Void> addItemToShop(@RequestBody ItemShopAssociation request) {
        itemShopAssociationService.addItemToShop(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("Обновление ссылки товара в магазине.")
    @PutMapping("/update")
    public ResponseEntity<ItemShopAssociation> updateItemUrlInShop(@RequestBody ItemShopAssociation request) {
        ItemShopAssociation response = itemShopAssociationService.updateItemShopAssociation(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Удаление товара из магазина.")
    @DeleteMapping("/{shopId}/{itemId}")
    public ResponseEntity<Void> removeItemFromShop(
            @PathVariable Long shopId,
            @PathVariable Long itemId) {
        try {
            itemShopAssociationService.removeItemFromShop(shopId, itemId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @ApiOperation("Просмотр записей о товарах в магазине.")
    @GetMapping("/{shopId}")
    public ResponseEntity<GetShopAssociationsResponse> getShopAssociations(@PathVariable Long shopId) {
        List<ItemShopAssociation> associations = itemShopAssociationService.getAssociationsByShopId(shopId);

        List<AssociatedItem> associatedItems = associations.stream()
                .map(association -> new AssociatedItem(association.getItemId(), association.getItemUrl()))
                .collect(Collectors.toList());

        GetShopAssociationsResponse response = new GetShopAssociationsResponse(shopId, associatedItems);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ApiOperation("Получение записи о товаре в магазине.")
    @GetMapping("/{shopId}/{itemId}")
    public ResponseEntity<ItemShopAssociation> getItemShopAssociation(@PathVariable Long itemId, @PathVariable Long shopId) {
        ItemShopAssociation association = itemShopAssociationService.getAssociationById(itemId, shopId);

        if (association != null) {
            return new ResponseEntity<>(association, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

