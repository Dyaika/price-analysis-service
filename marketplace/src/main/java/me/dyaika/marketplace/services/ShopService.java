package me.dyaika.marketplace.services;

import me.dyaika.marketplace.dto.ShopItem;
import me.dyaika.marketplace.entities.Item;
import me.dyaika.marketplace.entities.ItemShopPrice;
import me.dyaika.marketplace.entities.Shop;
import me.dyaika.marketplace.repositories.ItemRepository;
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
    private final ItemRepository itemRepository;
    private final ItemShopPriceRepository priceRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository,
                       ItemShopPriceRepository priceRepository,
                       NamedParameterJdbcTemplate template,
                       ItemRepository itemRepository) {
        this.shopRepository = shopRepository;
        this.template = template;
        this.priceRepository = priceRepository;
        this.itemRepository = itemRepository;
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
    public ShopItem getShopItem(Long shopId, Long itemId){
        ShopItem response = new ShopItem();
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isPresent()){
            response.setItemId(item.get().getItemId());
            response.setItemName(item.get().getItemName());
            response.setItemDescription(item.get().getItemDescription());
            response.setCategoryId(item.get().getCategoryId());
            response.setPrice(getActualPrice(shopId, itemId));
            return response;
        } else {
            return null;
        }
    }

    public BigDecimal getActualPrice(Long shopId, Long itemId) {
        List<ItemShopPrice> latestPrices = priceRepository.findLatestPricesByItemId(itemId);

        // Фильтруем результаты, чтобы оставить только те, которые относятся к нужному магазину
        List<ItemShopPrice> shopPrices = latestPrices.stream()
                .filter(price -> price.getShopId().equals(shopId))
                .toList();

        if (!shopPrices.isEmpty()) {
            // Выбираем последнюю цену для данного товара в этом магазине
            ItemShopPrice latestPrice = shopPrices.get(0);
            return latestPrice.getItemPrice();
        } else {
            return null;
        }
    }
}
