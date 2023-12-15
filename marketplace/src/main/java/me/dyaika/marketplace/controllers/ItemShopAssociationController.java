package me.dyaika.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.dyaika.marketplace.dto.AssociatedItem;
import me.dyaika.marketplace.dto.responses.GetShopAssociationsResponse;
import me.dyaika.marketplace.entities.ItemShopAssociation;
import me.dyaika.marketplace.security.RoleChecker;
import me.dyaika.marketplace.services.ItemShopAssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Ассоциация товар-магазин.", description = "Управление информацией о товарах в магазине.")
@RestController
@RequestMapping("/item-shop")
public class ItemShopAssociationController {

    private final ItemShopAssociationService itemShopAssociationService;
    private final RoleChecker roleChecker;

    @Autowired
    public ItemShopAssociationController(ItemShopAssociationService itemShopAssociationService, RoleChecker roleChecker) {
        this.itemShopAssociationService = itemShopAssociationService;
        this.roleChecker = roleChecker;
    }

    @ApiOperation("Добавление товара в магазин.")
    @PostMapping
    public ResponseEntity<Void> addItemToShop(@RequestBody ItemShopAssociation request, HttpServletRequest httpRequest) {
        if (roleChecker.checkRole("ADMIN", httpRequest)){
            itemShopAssociationService.addItemToShop(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation("Обновление ссылки товара в магазине.")
    @PutMapping("/update")
    public ResponseEntity<ItemShopAssociation> updateItemUrlInShop(@RequestBody ItemShopAssociation request, HttpServletRequest httpRequest) {
        if (roleChecker.checkRole("ADMIN", httpRequest)){
            ItemShopAssociation response = itemShopAssociationService.updateItemShopAssociation(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation("Удаление товара из магазина.")
    @DeleteMapping("/{shopId}/{itemId}")
    public ResponseEntity<Void> removeItemFromShop(
            @PathVariable Long shopId,
            @PathVariable Long itemId,
            HttpServletRequest httpRequest) {
        if (roleChecker.checkRole("ADMIN", httpRequest)){
            try {
                itemShopAssociationService.removeItemFromShop(shopId, itemId);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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

