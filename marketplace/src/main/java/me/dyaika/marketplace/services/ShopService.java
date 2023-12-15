package me.dyaika.marketplace.services;

import me.dyaika.marketplace.entities.ItemShopPrice;
import me.dyaika.marketplace.entities.Shop;
import me.dyaika.marketplace.repositories.ItemShopPriceRepository;
import me.dyaika.marketplace.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public ShopService(ShopRepository shopRepository, ItemShopPriceRepository priceRepository, NamedParameterJdbcTemplate template) {
        this.shopRepository = shopRepository;
        this.template = template;
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

    public void setActualPrice(Long shopId, Long itemId, BigDecimal price) {
        LocalDateTime currentDate = LocalDateTime.now();
        // Подготавливаем параметры для вставки
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", shopId);
        params.put("itemId", itemId);
        params.put("priceCheckDate", currentDate);
        params.put("itemPrice", price);
        String sql = "INSERT INTO item_shop_prices (shop_id, item_id, price_check_date, item_price) VALUES (:shopId, :itemId, :priceCheckDate, :itemPrice)";
        template.update(sql, params);
    }
}
