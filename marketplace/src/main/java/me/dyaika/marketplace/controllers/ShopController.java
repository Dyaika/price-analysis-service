package me.dyaika.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.dyaika.marketplace.entities.Shop;
import me.dyaika.marketplace.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Магазины.", description = "Управление магазинами.")
@RestController
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
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
    @PostMapping
    public Shop createShop(@RequestBody Shop shop) {
        return shopService.saveShop(shop);
    }

    @ApiOperation("Обновить информацию о существующем магазине.")
    @PutMapping("/{shopId}")
    public Shop updateShop(@PathVariable Long shopId, @RequestBody Shop updatedShop) {
        Shop existingShop = shopService.getShopById(shopId);
        if (existingShop != null) {
            // Обновляем информацию
            existingShop.setShopName(updatedShop.getShopName());
            existingShop.setShopDescription(updatedShop.getShopDescription());
            existingShop.setShopUrl(updatedShop.getShopUrl());
            return shopService.saveShop(existingShop);
        }
        return null; // Можно также вернуть ResponseEntity с кодом 404, чтобы показать, что магазин не найден
    }

    @ApiOperation("Удалить магазин по его ID.")
    @DeleteMapping("/{shopId}")
    public void deleteShop(@PathVariable Long shopId) {
        shopService.deleteShop(shopId);
    }
}

