package me.dyaika.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.dyaika.marketplace.dto.requests.SetActualPriceRequest;
import me.dyaika.marketplace.dto.ShopItem;
import me.dyaika.marketplace.dto.responses.GetShopItemsIdResponse;
import me.dyaika.marketplace.dto.responses.GetShopItemsResponse;
import me.dyaika.marketplace.entities.ItemShopAssociation;
import me.dyaika.marketplace.entities.Shop;
import me.dyaika.marketplace.security.RoleChecker;
import me.dyaika.marketplace.services.ItemShopAssociationService;
import me.dyaika.marketplace.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Api(tags = "Магазины.", description = "Управление магазинами.")
@RestController
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;
    private final ItemShopAssociationService associationService;
    private final RoleChecker roleChecker;

    @Autowired
    public ShopController(ShopService shopService, ItemShopAssociationService associationService, RoleChecker roleChecker) {
        this.shopService = shopService;
        this.associationService = associationService;
        this.roleChecker = roleChecker;
    }

    @ApiOperation("Получить список всех магазинов.")
    @GetMapping("/all")
    public List<Shop> getAllShops() {
        return shopService.getAllShops();
    }

    @ApiOperation("Получить информацию о магазине по его ID.")
    @GetMapping("/{shopId}")
    public Shop getShopById(@PathVariable Long shopId) {
        return shopService.getShopById(shopId);
    }

    @ApiOperation("Создать новый магазин.")
    @PostMapping("/create")
    public ResponseEntity<Shop> createShop(@RequestBody Shop shop, HttpServletRequest httpRequest) {
        if (roleChecker.checkRole("ADMIN", httpRequest)){
            return new ResponseEntity<>(shopService.saveShop(shop), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation("Обновить информацию о существующем магазине.")
    @PutMapping("/{shopId}")
    public ResponseEntity<Shop> updateShop(@PathVariable Long shopId, @RequestBody Shop updatedShop, HttpServletRequest httpRequest) {
        if (roleChecker.checkRole("ADMIN", httpRequest)){
            Shop existingShop = shopService.getShopById(shopId);
            if (existingShop != null) {
                // Обновляем информацию
                existingShop.setShopName(updatedShop.getShopName());
                existingShop.setShopDescription(updatedShop.getShopDescription());
                existingShop.setShopUrl(updatedShop.getShopUrl());
                return new ResponseEntity<>(shopService.saveShop(existingShop), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation("Удалить магазин по его ID.")
    @DeleteMapping("/{shopId}")
    public ResponseEntity<Long> deleteShop(@PathVariable Long shopId, HttpServletRequest httpRequest) {
        if (roleChecker.checkRole("ADMIN", httpRequest)){
            try {
                shopService.deleteShop(shopId);
                return new ResponseEntity<>(shopId, HttpStatus.OK);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation("Просмотр идентификаторов товаров по магазину.")
    @GetMapping("/{shopId}/items-id")
    public ResponseEntity<GetShopItemsIdResponse> getShopItemsId(@PathVariable Long shopId) {
        List<Long> itemsId = associationService.getAssociationsByShopId(shopId).stream()
                .map(ItemShopAssociation::getItemId)
                .toList();

        GetShopItemsIdResponse response = new GetShopItemsIdResponse(shopId, itemsId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Просмотр товаров по магазину.")
    @GetMapping("/{shopId}/items")
    public ResponseEntity<GetShopItemsResponse> getShopItems(@PathVariable Long shopId) {
        List<ShopItem> items = associationService.getAssociationsByShopId(shopId).stream()
                .map(association -> shopService.getShopItem(shopId, association.getItemId()))
                .toList();

        GetShopItemsResponse response = new GetShopItemsResponse(shopId, items);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation("Уставновить цену на товар.")
    @PostMapping("/{shopId}/set-price")
    public ResponseEntity<Void> setActualPrice(@PathVariable Long shopId, @RequestBody SetActualPriceRequest request, HttpServletRequest httpRequest) {
        if (roleChecker.checkRole("ADMIN", httpRequest)){
            shopService.setActualPrice(shopId, request.getItemId(), request.getPrice());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @ApiOperation("Узнать о товаре в магазине.")
    @GetMapping("/{shopId}/items/{itemId}")
    public ResponseEntity<ShopItem> getShopItem(@PathVariable Long shopId, @PathVariable Long itemId) {
        ShopItem response = shopService.getShopItem(shopId, itemId);

        if (response != null){
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Получить актуальную цену для товара в магазине.")
    @GetMapping("/{shopId}/items/{itemId}/price")
    public ResponseEntity<BigDecimal> getActualPrice(@PathVariable Long shopId, @PathVariable Long itemId) {
        BigDecimal price = shopService.getActualPrice(shopId, itemId);
        if (price != null){
            return new ResponseEntity<>(price, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

