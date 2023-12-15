package me.dyaika.marketplace.dto.responses;

import lombok.Data;
import me.dyaika.marketplace.dto.ShopItem;

import java.util.List;

@Data
public class GetShopItemsResponse {
    private Long shopId;

    public GetShopItemsResponse(Long shopId, List<ShopItem> items) {
        this.shopId = shopId;
        this.items = items;
    }

    private List<ShopItem> items;
}
