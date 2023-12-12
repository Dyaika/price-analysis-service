package me.dyaika.marketplace.services;

import me.dyaika.marketplace.entities.Shop;
import me.dyaika.marketplace.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    public Shop getShopById(Long shopId) {
        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        return optionalShop.orElse(null);
    }

    public Shop saveShop(Shop shop) {
        return shopRepository.save(shop);
    }

    public void deleteShop(Long shopId) {
        shopRepository.deleteById(shopId);
    }
}
